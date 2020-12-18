package com.lzx.spring.cloud.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("spring-cloud-account-server")
public interface AccountService {

    @PostMapping("/account/reduce-balance")
    Boolean reduceBalance(@RequestParam("userId") Long userId, @RequestParam("price") Integer price) throws Exception;

}
