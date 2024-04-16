package com.jmalltech.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redisson")
public class MyRedissonProperties {
    //线程池数量,默认值 =当前处理核数量 *2


    private int threads;
    //Netty线程池数量,默认值 =当前处理核数量 *2


    private int nettyThreads;

    //单机服务配置


    private SingleServerConfig singleServerConfig;


    @Data
    @NoArgsConstructor
    public static class SingleServerConfig {
        //客户端名称（在Redis节点里显示的客户端名称）。默认值:null


        private String clientName;
        //密码。默认值:null


        private String password;
        //地址，示例：redis://192.168.1.128:6379


        private String address;
        // 最小空闲连接数


        private int connectionMinimumIdleSize;
        // 连接池大小


        private int connectionPoolSize;
        // 连接空闲超时，单位：毫秒


        private int idleConnectionTimeout;
        //命令等待超时，单位：毫秒


        private int timeout;


        // 数据库编号。默认值: 0


        private int database;

        //* 连接超时，单位：毫秒。默认值: 10000


        private long connectTimeout;
        //* 命令失败重试次数。默认值: 3


        private int retryAttempts;
        //* 发布和订阅连接的最小空闲连接数。默认值: 1


        private int subscriptionConnectionMinimumIdleSize;
        //* 发布和订阅连接池大小。默认值: 50


        private int subscriptionConnectionPoolSize;


    }
}


