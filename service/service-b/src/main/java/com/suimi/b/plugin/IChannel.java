/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2017-05-19.
 */
package com.suimi.b.plugin;

import org.springframework.plugin.core.Plugin;

public interface IChannel extends Plugin<Channel> {

    String process(String request);

    Channel channel();
}
