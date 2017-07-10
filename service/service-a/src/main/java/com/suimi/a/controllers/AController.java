/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2016-12-27.
 */
package com.suimi.a.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.suimi.a.exception.AException;
import com.suimi.a.exception.AException.ErrorMsg;
import com.suimi.a.feign.BService;
import com.suimi.b.client.ReduceClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("Aservice")
@Api(value = "Aservice", description = "A服务类")
@Slf4j
@RefreshScope
public class AController {

    @Autowired
    private BService bService;

    @Autowired
    private ReduceClient reduceClient;

    @ApiOperation(value = "client 测试", notes = "只为测试")
    @ApiImplicitParams({@ApiImplicitParam(name = "word", value = "hello word", required = true, dataType = "String")})
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @HystrixCommand
    public String hello(String word) {
        log.info("hello {}", word);
        return "hello " + word;
    }

    @ApiOperation(value = "测试", notes = "测试方法")
    @ApiImplicitParams({@ApiImplicitParam(name = "word", value = "test word", required = true, dataType = "String")})
    @RequestMapping(value = "test", method = RequestMethod.GET)
    @HystrixCommand
    public String test(String word) {
        log.info("test {}", word);
        return "test " + word;
    }

    @ApiOperation(value = "测试", notes = "测试Get方法")
    @ApiImplicitParams({@ApiImplicitParam(name = "word", value = "get word", required = true, dataType = "String")})
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @HystrixCommand
    public String get(String word) {
        log.info("get {}", word);
        return "get " + word;
    }

    @ApiOperation(value = "测试配置", notes = "测试配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "word", value = "get word", required = true, dataType = "String")})
    @RequestMapping(value = "testConfig", method = RequestMethod.GET)
    @HystrixCommand
    public String testConfig(String word) {
        log.info("test value: {}", "");
        return "get " + word;
    }

    @ApiOperation(value = "加", notes = "两数相加")
    @ApiImplicitParams({@ApiImplicitParam(name = "a", value = "参数a", required = true, paramType = "path", dataType = "int"), @ApiImplicitParam(name = "b", value = "参数b", required = true, paramType = "path", dataType = "int")})
    @RequestMapping(value = "add/{a}/{b}", method = RequestMethod.GET)
    @HystrixCommand
    public int add(@PathVariable("a") int a, @PathVariable("b") int b) {
        return bService.add(a, b);
    }

    @ApiOperation(value = "减", notes = "两数相加")
    @ApiImplicitParams({@ApiImplicitParam(name = "a", value = "参数a", required = true, paramType = "path", dataType = "int"), @ApiImplicitParam(name = "b", value = "参数b", required = true, paramType = "path", dataType = "int")})
    @RequestMapping(value = "reduce/{a}/{b}", method = RequestMethod.GET)
    @HystrixCommand
    public int reduce(@PathVariable("a") int a, @PathVariable("b") int b) {
        return reduceClient.reduce(a, b);
    }


    @ApiOperation(value = "测试异常", notes = "测试异常")
    @ApiImplicitParams({@ApiImplicitParam(name = "error", value = "error word", required = true, dataType = "String")})
    @RequestMapping(value = "error", method = RequestMethod.GET)
    public String error(String error) {
        if ("error".equals(error)) {
            throw new AException(new ErrorMsg("99", "系统错误"));
        }
        return error;
    }
}
