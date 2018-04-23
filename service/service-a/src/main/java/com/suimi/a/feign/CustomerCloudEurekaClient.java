/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.suimi.a.feign;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.*;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.discovery.shared.transport.EurekaHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

/**
 * 重写获取instance info方法
 */
@Slf4j public class CustomerCloudEurekaClient extends CloudEurekaClient {

    public CustomerCloudEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig config,
        ApplicationEventPublisher publisher) {
        super(applicationInfoManager, config, publisher);
    }

    public CustomerCloudEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig config,
        AbstractDiscoveryClientOptionalArgs<?> args, ApplicationEventPublisher publisher) {
        super(applicationInfoManager, config, args, publisher);
    }

    ////////////////////////////////////////
    // customize change                   //
    ////////////////////////////////////////
    @Override public List<InstanceInfo> getInstancesByVipAddress(String vipAddress, boolean secure, String region) {
        if (log.isTraceEnabled()) {
            log.trace("vipAddress:{},sesure:{},region:{}", vipAddress, secure, region);
        }
        if (vipAddress == null) {
            throw new IllegalArgumentException("Supplied VIP Address cannot be null");
        }
        Applications applications = getApplicationsForARegion(region);

        if (!secure) {
            //customize change
            List<InstanceInfo> instances = getInstances(applications, vipAddress);
            if (log.isTraceEnabled()) {
                log.trace("Client vipAddress instances:{}", ToStringBuilder.reflectionToString(instances));
            }
            return instances;
        } else {
            //todo:suimi 如果需要则重写
            return applications.getInstancesBySecureVirtualHostName(vipAddress);

        }
    }

    //todo:suimi
    @Override public List<InstanceInfo> getInstancesByVipAddressAndAppName(String vipAddress, String appName,
        boolean secure) {
        return super.getInstancesByVipAddressAndAppName(vipAddress, appName, secure);
    }

    private List<InstanceInfo> getInstances(Applications applications, String appName) {
        if (log.isTraceEnabled()) {
            log.trace("Instances default method:{}", applications.getInstancesByVirtualHostName(appName));
        }
        List<InstanceInfo> instanceInfos = new ArrayList<>();
        applications.getRegisteredApplications().forEach(application -> {
            if (appName.equalsIgnoreCase(application.getName()) || application.getName()
                .startsWith(appName.toUpperCase(Locale.ROOT))) {
                instanceInfos.addAll(application.getInstances());
            }
        });
        if (log.isTraceEnabled()) {
            log.trace("Instances my method:{}", instanceInfos);
        }
        return instanceInfos;
    }
}
