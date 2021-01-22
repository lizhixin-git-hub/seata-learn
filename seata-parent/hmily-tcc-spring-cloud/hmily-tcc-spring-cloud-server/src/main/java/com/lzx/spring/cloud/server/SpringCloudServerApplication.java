package com.lzx.spring.cloud.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * https://github.com/dromara/hmily/tree/master/hmily-demo
 * 分布式无法用本地事务来解决：
 * 假设a服务调用b服务成功，b服务响应由于网络超时，导致a服务数据回滚，但b服务数据已提交，将导致数据不一致情况。
 * 分布式系统环境下由不同的服务之间通过网络远程协作完成事务称之为分布式事务；
 * 分布式事务的场景：
 * 1、微服务架构(跨JVM进程产生分布式事务)
 * 2、单体系统访问多个数据库实例（跨数据库实例产生分布式事务）
 * 3、多个服务访问同一个数据库实例（跨JVM进程）
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudServerApplication.class, args);
    }

}
