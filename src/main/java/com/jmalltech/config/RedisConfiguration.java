package com.jmalltech.config;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableCaching
public class RedisConfiguration {
    @Autowired
    private MyRedissonProperties redissonProperties;
    private static final Integer timeout = 600;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setThreads(redissonProperties.getThreads())
                .setNettyThreads(redissonProperties.getNettyThreads())
                .setCodec(new JsonJacksonCodec());
        MyRedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
        if(ObjectUtil.isNotNull(singleServerConfig)){
            config.useSingleServer()
                    .setAddress(singleServerConfig.getAddress())
                    .setDatabase(singleServerConfig.getDatabase())
                    //.setPassword(singleServerConfig.getPassword()) dont need to set password if redis db don't have password
                    .setClientName(singleServerConfig.getClientName())
                    .setConnectTimeout(singleServerConfig.getTimeout())
                    .setRetryAttempts(singleServerConfig.getRetryAttempts())
                    .setTimeout(singleServerConfig.getTimeout())
                    .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
                    .setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
                    .setSubscriptionConnectionMinimumIdleSize(singleServerConfig.getSubscriptionConnectionMinimumIdleSize())
                    .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
                    .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize());
        }else {
            throw new RuntimeException("redisson配置错误");
        }
        log.info("初始化 redis 配置");
        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        return new RedissonSpringCacheManager(redissonClient);
    }
}
