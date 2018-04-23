/*
 *
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.suimi.a.feign;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Slf4j public class RequestHeaderRoundRobinRule extends RoundRobinRule {
    private AtomicInteger nextServerCyclicCounter;

    public RequestHeaderRoundRobinRule() {
        nextServerCyclicCounter = new AtomicInteger(0);
    }

    public RequestHeaderRoundRobinRule(ILoadBalancer lb) {
        this();
        setLoadBalancer(lb);
    }

    /**
     * Randomly choose from all living servers
     */
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        Server server = null;
        int count = 0;
        while (server == null && count++ < 10) {
            List<Server> reachableServers = lb.getReachableServers();
            List<Server> allServers = lb.getAllServers();
            if (log.isTraceEnabled()) {
                log.trace("All servers:{}", ToStringBuilder.reflectionToString(allServers));
                log.trace("All reachable servers:{}", ToStringBuilder.reflectionToString(reachableServers));
            }
            reachableServers = choose(reachableServers, key);
            allServers = choose(allServers, key);
            if (log.isTraceEnabled()) {
                log.trace("All servers:{}", ToStringBuilder.reflectionToString(allServers));
                log.trace("All reachable servers:{}", ToStringBuilder.reflectionToString(reachableServers));
            }
            int upCount = reachableServers.size();
            int serverCount = allServers.size();

            if ((upCount == 0) || (serverCount == 0)) {
                log.warn("No up servers available from load balancer: " + lb);
                return null;
            }

            int nextServerIndex = incrementAndGetModulo(serverCount);
            server = allServers.get(nextServerIndex);

            if (server == null) {
                /* Transient. */
                Thread.yield();
                continue;
            }

            if (server.isAlive() && (server.isReadyToServe())) {
                return (server);
            }

            // Next.
            server = null;
        }

        if (count >= 10) {
            log.warn("No available alive servers after 10 tries from load balancer: " + lb);
        }
        return server;

    }

    @Override public Server choose(Object key) {
        Server server = choose(getLoadBalancer(), key);
        log.info("Choosed server:{}", server);
        return server;
    }

    @Override public void setLoadBalancer(ILoadBalancer lb) {
        super.setLoadBalancer(lb);
    }

    private List<Server> choose(List<Server> servers, Object key) {
        if (key == null) {
            return servers;
        }
        if (key instanceof HttpHeaders) {
            HttpHeaders headers = (HttpHeaders)key;
            if (headers.containsKey(FeignRibbonConstants.NODE_NAME_REG)) {
                String first = headers.getFirst(FeignRibbonConstants.NODE_NAME_REG);
                if (StringUtils.isNotBlank(first)) {
                    List<Server> matchedServers = new ArrayList<>();
                    Pattern pattern = Pattern.compile(first.toUpperCase(Locale.ROOT));
                    servers.forEach(server -> {
                        if (pattern.matcher(server.getMetaInfo().getAppName()).matches()) {
                            matchedServers.add(server);
                        }
                    });
                    return matchedServers;
                }
            }
        }
        return servers;
    }

    /**
     * Inspired by the implementation of {@link AtomicInteger#incrementAndGet()}.
     *
     * @param modulo The modulo to bound the value of the counter.
     * @return The next value.
     */
    private int incrementAndGetModulo(int modulo) {
        for (; ; ) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next))
                return next;
        }
    }
}
