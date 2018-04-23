/*
 * Copyright (c) 2013-2017, suimi
 */
package com.suimi.a.feign;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author suimi
 * @date 2018/4/20
 */
@Configuration
public class ClientConfig {

    @Bean public IRule ribbonRule() {
        return new RequestHeaderRoundRobinRule();
    }



}
