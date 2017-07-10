/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2016-12-27.
 */
package com.suimi.b.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.suimi.b.client.ReduceApi;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BController implements ReduceApi {

    @Autowired
    private BService service;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @HystrixCommand
    public String hello(String word) {
        log.info("hello {}", word);
        return "hello " + word;
    }

    @HystrixCommand
    @RequestMapping(value = "add/{a}/{b}", method = RequestMethod.GET)
    public int add(@PathVariable("a") int a, @PathVariable("b") int b) {
        log.info("add {} and {}", a, b);
        int add = service.add(a, b);
        log.info("result {}", add);
        return add;
    }

    @RequestMapping(value = "channel", method = RequestMethod.GET)
    public String channel(String request) {
        return service.channel(request);
    }

    public int reduce(@PathVariable("a") int a, @PathVariable("b") int b) {
        return a - b;
    }
}
