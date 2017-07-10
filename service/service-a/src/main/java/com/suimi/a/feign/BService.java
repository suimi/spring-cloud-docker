/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2017-03-02.
 */
package com.suimi.a.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("SERVICE-B")
public interface BService {

    @RequestMapping(value = "add/{a}/{b}", method = RequestMethod.GET)
    int add(@PathVariable("a") int a, @PathVariable("b") int b);
}
