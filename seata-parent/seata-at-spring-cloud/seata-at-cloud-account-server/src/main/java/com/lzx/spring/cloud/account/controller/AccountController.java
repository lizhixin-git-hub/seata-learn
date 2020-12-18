package com.lzx.spring.cloud.account.controller;

import com.lzx.spring.cloud.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/reduce-balance")
    public void reduceBalance(@RequestParam("userId") Long userId, @RequestParam("price") Integer price) throws Exception {
        logger.info("[reduceBalance] 收到减少余额请求, 用户:{}, 金额:{}", userId, price);

        accountService.reduceBalance(userId, price);
    }

}