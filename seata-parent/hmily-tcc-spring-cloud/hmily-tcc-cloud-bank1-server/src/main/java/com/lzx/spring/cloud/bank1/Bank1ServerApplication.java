package com.lzx.spring.cloud.bank1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *  理论上只要try成功，confirm一定成功，如果confirm失败，不会执行cancel，会进行重试
 *  整合：https://blog.csdn.net/qq_25805331/article/details/109692325
 *  1) 空回滚 ：就是 try没有执行 就执行cancel 方法。
 *  2）幂等性 ：一个方法无论调多少次 结果都是一样的 这是幂等性。
 *  3) 悬挂 ；confirm 或者 cancel已经执行了 又要执行 try方法 这是悬挂。
 *  （目前新的版本Hmily是已经完成了对空回滚、悬挂的自动处理，无需开发者自行处理，代码中已经自行处理，请忽略该部分处理的代码）
 * 模拟bank1向bank2转账
 * 官方文档：https://dromara.org/zh-cn/docs/hmily/config.html
 * https://www.liangzl.com/get-article-detail-124541.html
 * https://blog.csdn.net/github_38592071/article/details/103370578
 * https://blog.csdn.net/qq_35599414/article/details/110070840
 * Hmily通过ServerConfigLoader类加载hmily.yml文件，HmilyAutoConfiguration类添加了
 * //@EnableAspectJAutoProxy(
 *     proxyTargetClass = true
 * )注解，如果未使用start包，需在启动类上加上该注解，手动编写配置类，构建HmilyBootstrap对象，加载配置信息,
 * 封装成HmilyConfig
 */
@EnableFeignClients
@MapperScan("com.lzx.spring.cloud.bank1.dao")
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class Bank1ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Bank1ServerApplication.class, args);
    }

}
