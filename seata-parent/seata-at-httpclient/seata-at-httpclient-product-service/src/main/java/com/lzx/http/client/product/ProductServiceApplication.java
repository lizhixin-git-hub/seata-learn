package com.lzx.http.client.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用httpclient应用seata
 * https://github.com/YunaiV/SpringBoot-Labs/blob/master/lab-52/
 * http://www.iocoder.cn/Spring-Boot/Seata/?self
 * http://seata.io/zh-cn/blog/seata-quick-start.html
 */
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
