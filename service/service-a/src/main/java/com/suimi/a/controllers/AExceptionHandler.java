/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2017-05-22.
 */
package com.suimi.a.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.suimi.a.exception.AException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice({"com.suimi.hello.controllers"})
@Slf4j
public class AExceptionHandler {

    @ExceptionHandler(AException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public Object exceptionHandler(AException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getErrorMsg();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public Object exceptionHandler(Exception ex) {
        return ex.getMessage();
    }
}
