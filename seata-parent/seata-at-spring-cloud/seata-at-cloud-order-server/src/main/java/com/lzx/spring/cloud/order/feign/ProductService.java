package com.lzx.spring.cloud.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("spring-cloud-product-server")
public interface ProductService {

    @PostMapping("/product/reduce-stock")
    Boolean reduceStock(@RequestParam("productId") Long productId, @RequestParam("amount") Integer amount) throws Exception;

}
