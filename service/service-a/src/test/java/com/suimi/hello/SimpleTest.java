/*
 * Copyright (c) 2013-2017, suimi
 */
package com.suimi.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author suimi
 * @date 2018/4/23
 */
@Slf4j
public class SimpleTest {
    @Test
    public void test(){
        String reg = "(?!SERVICE-A)SERVICE(\\S)*";
        log.info("result:{}", "SERVICE".matches(reg));
        log.info("result:{}", "SERVICE-A".matches(reg));
        log.info("result:{}", "SERVICE-B".matches(reg));
        log.info("result:{}", "SERVICE-C".matches(reg));
    }
}
