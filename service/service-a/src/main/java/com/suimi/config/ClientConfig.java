/*
 * Copyright (c) 2013-2017, suimi
 */
package com.suimi.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.suimi.a.feign.RequestHeaderRoundRobinRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author suimi
 * @date 2018/4/20
 */
@Configuration
@Import(RibbonClientConfiguration.class)
public class ClientConfig {

    @Autowired private PropertiesFactory propertiesFactory;

    @Bean public IRule ribbonRule(IClientConfig config) {
        if (this.propertiesFactory.isSet(IRule.class, config.getClientName())) {
            return this.propertiesFactory.get(IRule.class, config, config.getClientName());
        }
        RequestHeaderRoundRobinRule rule = new RequestHeaderRoundRobinRule();
        rule.initWithNiwsConfig(config);
        return rule;
    }

}
