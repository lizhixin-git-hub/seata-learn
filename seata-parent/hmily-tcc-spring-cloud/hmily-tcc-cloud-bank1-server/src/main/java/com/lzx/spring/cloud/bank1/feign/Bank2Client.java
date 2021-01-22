package com.lzx.spring.cloud.bank1.feign;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("spring-cloud-bank2-server")
public interface Bank2Client {

    /**
     * 远程调用bank2增加金额
     * @param amount 增减金额
     * @return 增加结果
     */
    //这个注解 为的是 把全局的 事务id 传到 下游
    @Hmily
    @GetMapping("/bank2/transfer")
    Boolean transfer(@RequestParam("amount") Double amount);

}
