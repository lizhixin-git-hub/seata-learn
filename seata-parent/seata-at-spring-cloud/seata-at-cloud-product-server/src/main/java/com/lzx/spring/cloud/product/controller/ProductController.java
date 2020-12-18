package com.lzx.spring.cloud.product.controller;

import com.lzx.spring.cloud.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/reduce-stock")
    public void reduceStock(@RequestParam("productId") Long productId, @RequestParam("amount") Integer amount) throws Exception {
        logger.info("[reduceStock] 收到减少库存请求, 商品:{}, 价格:{}", productId, amount);

        productService.reduceStock(productId, amount);
    }

}