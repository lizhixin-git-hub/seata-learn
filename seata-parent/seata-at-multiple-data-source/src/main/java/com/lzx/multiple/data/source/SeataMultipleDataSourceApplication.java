package com.lzx.multiple.data.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SEATA AT 模式 + 多数据源
 */
@SpringBootApplication
public class SeataMultipleDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataMultipleDataSourceApplication.class, args);
    }

}
