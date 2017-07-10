/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2017-05-19.
 */
package com.suimi.b.plugin;

import org.springframework.stereotype.Component;

@Component
public class BChannel implements IChannel {

    private Channel channel = Channel.B;

    public String process(String request) {
        return channel.name() + ":" + request;
    }

    public Channel channel() {
        return channel;
    }

    public boolean supports(Channel delimiter) {
        return channel == delimiter;
    }
}
