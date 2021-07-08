package com.lzx.spring.cloud.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 快速入门路径：https://seata.io/zh-cn/blog/seata-quick-start.html
 * http://www.iocoder.cn/Spring-Cloud-Alibaba/Seata/?self
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudServerApplication.class, args);
    }

}
