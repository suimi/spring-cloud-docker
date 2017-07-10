/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2017-03-02.
 */
package com.suimi.b.client;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("SERVICE-B")
public interface ReduceClient extends ReduceApi {

}
