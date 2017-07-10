/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2017-03-02.
 */
package com.suimi.b.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "减法服务", description = "B服务类")
public interface ReduceApi {

    @ApiOperation(value = "减", notes = "两数相减")
    @ApiImplicitParams({@ApiImplicitParam(name = "a", value = "参数a", required = true, paramType = "path", dataType = "int"), @ApiImplicitParam(name = "b", value = "参数b", required = true, paramType = "path", dataType = "int")})
    @RequestMapping(value = "reduce/{a}/{b}", method = RequestMethod.GET)
    int reduce(@PathVariable("a") int a, @PathVariable("b") int b);
}
