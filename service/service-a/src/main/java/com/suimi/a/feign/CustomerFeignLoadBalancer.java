/*
 * Copyright 2015 the original author or authors.
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

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import org.springframework.cloud.netflix.ribbon.ServerIntrospector;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;

public class CustomerFeignLoadBalancer extends FeignLoadBalancer {

    public CustomerFeignLoadBalancer(ILoadBalancer lb, IClientConfig clientConfig,
        ServerIntrospector serverIntrospector) {
        super(lb, clientConfig, serverIntrospector);
    }

    ////////////////////////////////////////
    // customize change                   //
    ////////////////////////////////////////

    /**
     * support loadBalancerKey
     *
     * @param request
     * @param config
     * @param builder
     */
    @Override protected void customizeLoadBalancerCommandBuilder(FeignLoadBalancer.RibbonRequest request,
        IClientConfig config, LoadBalancerCommand.Builder<FeignLoadBalancer.RibbonResponse> builder) {
        super.customizeLoadBalancerCommandBuilder(request, config, builder);
        HttpHeaders headers = request.toHttpRequest().getHeaders();
        builder.withServerLocator(headers);
    }
}