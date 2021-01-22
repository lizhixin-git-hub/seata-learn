package com.lzx.spring.cloud.bank2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * https://www.liangzl.com/get-article-detail-124541.html
 * https://blog.csdn.net/github_38592071/article/details/103370578
 * https://blog.csdn.net/qq_35599414/article/details/110070840
 * Hmily通过ServerConfigLoader类加载hmily.yml文件，HmilyAutoConfiguration类添加了
 * //@EnableAspectJAutoProxy(
 *     proxyTargetClass = true
 * )注解，如果未使用start包，需在启动类上加上该注解，手动编写配置类，构建HmilyBootstrap对象，加载配置信息,
 * 封装成HmilyConfig
 */
@MapperScan("com.lzx.spring.cloud.bank2.dao")
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class Bank2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Bank2ServerApplication.class, args);
    }

}
