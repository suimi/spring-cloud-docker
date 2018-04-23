/*
 * Copyright (c) 2013-2017, suimi
 */
package com.suimi.a;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.suimi.a.feign.CustomerCachingSpringLoadBalancerFactory;
import com.suimi.a.feign.CustomerCloudEurekaClient;
import com.suimi.a.feign.CustomerLoadBalancerFeignClient;
import feign.Client;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.client.loadbalancer.LoadBalancedBackOffPolicyFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryListenerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryPolicyFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author suimi
 * @date 2018/4/19
 */
//@Configuration @AutoConfigureBefore(RibbonClientConfiguration.class)
public class FeignConfig {

    @Bean(destroyMethod = "shutdown")
    public EurekaClient eurekaClient(ApplicationInfoManager manager, EurekaClientConfig config,
        ApplicationContext context, AbstractDiscoveryClientOptionalArgs<?> optionalArgs) {
        return new CustomerCloudEurekaClient(manager, config, optionalArgs, context);
    }

    @Bean public CustomerCachingSpringLoadBalancerFactory retryableCachingLBClientFactory(SpringClientFactory factory,
        LoadBalancedRetryPolicyFactory retryPolicyFactory,
        LoadBalancedBackOffPolicyFactory loadBalancedBackOffPolicyFactory,
        LoadBalancedRetryListenerFactory loadBalancedRetryListenerFactory) {
        return new CustomerCachingSpringLoadBalancerFactory(factory, retryPolicyFactory,
            loadBalancedBackOffPolicyFactory, loadBalancedRetryListenerFactory);
    }

    @Bean public Client feignClient(CustomerCachingSpringLoadBalancerFactory cachingFactory,
        SpringClientFactory clientFactory) {
        return new CustomerLoadBalancerFeignClient(new Client.Default(null, null), cachingFactory, clientFactory);
    }

}
