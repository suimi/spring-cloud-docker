/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2017-03-31.
 */
package com.suimi.b.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Service;

import com.suimi.b.plugin.Channel;
import com.suimi.b.plugin.IChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BService implements InitializingBean {

    @Qualifier("iChannelRegistry")
    @Autowired
    private PluginRegistry<IChannel, Channel> channels;

    private Map<Channel, IChannel> channelMap = new HashMap<Channel, IChannel>();


    public int add(int a, int b) {
        log.info("{}+{}", a, b);
        return a + b;
    }

    public String channel(String request) {
        StringBuffer sb = new StringBuffer();
        for (IChannel channel : channels) {
            sb.append(channel.process(request));
            sb.append(" ");
        }
        return sb.toString();
    }


    public void afterPropertiesSet() throws Exception {
        for (IChannel channel : channels) {
            channelMap.put(channel.channel(), channel);
        }
    }
}
