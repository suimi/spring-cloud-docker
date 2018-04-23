/*
 *
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.suimi.a.feign;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.ribbon.proxy.annotation.Http;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

@Slf4j public class RequestHeaderRandomRule extends AbstractLoadBalancerRule {
    Random rand;

    public RequestHeaderRandomRule() {
        rand = new Random();
    }

    /**
     * Randomly choose from all living servers
     */
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();
            if (log.isTraceEnabled()) {
                log.trace("All servers:{}",ToStringBuilder.reflectionToString(allList));
                log.trace("All up servers:{}",ToStringBuilder.reflectionToString(upList));
            }
            upList = choose(upList, key);
            allList = choose(allList, key);
            if (log.isTraceEnabled()) {
                log.trace("All choosed servers:{}",ToStringBuilder.reflectionToString(allList));
                log.trace("All choosed up servers:{}",ToStringBuilder.reflectionToString(upList));
            }
            int serverCount = allList.size();
            if (serverCount == 0) {
                /*
                 * No servers. End regardless of pass, because subsequent passes
                 * only get more restrictive.
                 */
                return null;
            }

            int index = rand.nextInt(serverCount);
            server = upList.get(index);

            if (server == null) {
                /*
                 * The only time this should happen is if the server list were
                 * somehow trimmed. This is a transient condition. Retry after
                 * yielding.
                 */
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }

        return server;

    }

    @Override public Server choose(Object key) {
        Server server = choose(getLoadBalancer(), key);
        log.info("Choosed server:{}",server);
        return server;
    }

    @Override public void initWithNiwsConfig(IClientConfig clientConfig) {
        // TODO Auto-generated method stub
    }

    private List<Server> choose(List<Server> servers, Object key) {
        if (key == null) {
            return servers;
        }
        if (key instanceof HttpHeaders) {
            HttpHeaders headers = (HttpHeaders)key;
            if (headers.containsKey(FeignRibbonConstants.NODE_NAME_REG)) {
                String reg = headers.getFirst(FeignRibbonConstants.NODE_NAME_REG);
                if (StringUtils.isNotBlank(reg)) {
                    List<Server> matchedServers = new ArrayList<>();
                    servers.forEach(server -> {
                        String appName = server.getMetaInfo().getAppName().toUpperCase(Locale.ROOT);
                        if (appName.matches(reg)) {
                            matchedServers.add(server);
                        }
                    });
                    return matchedServers;
                }
            }
        }
        return servers;
    }
}
