/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2017-03-02.
 */
package com.suimi.a.feign;

import com.suimi.config.ClientConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "SERVICE")
public interface Service {

    @RequestMapping(value = "add/{a}/{b}", method = RequestMethod.GET) int add(@PathVariable("a") int a,
        @PathVariable("b") int b);

    @RequestMapping(value = "reduce/{a}/{b}", method = RequestMethod.GET) int reduce(
        @RequestHeader(FeignRibbonConstants.NODE_NAME_REG) String nodeName, @PathVariable("a") int a,
        @PathVariable("b") int b);
}
