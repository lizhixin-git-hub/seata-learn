package com.lzx.http.client.account.controller;

import com.lzx.http.client.account.dto.AccountReduceBalanceDTO;
import com.lzx.http.client.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Boolean reduceBalance(@RequestBody AccountReduceBalanceDTO accountReduceBalanceDTO) {
        logger.info("[reduceBalance] 收到减少余额请求, 用户:{}, 金额:{}", accountReduceBalanceDTO.getUserId(),
                accountReduceBalanceDTO.getPrice());
        try {
            accountService.reduceBalance(accountReduceBalanceDTO.getUserId(), accountReduceBalanceDTO.getPrice());
            // 正常扣除余额，返回 true
            return true;
        } catch (Exception e) {
            // 失败扣除余额，返回 false
            return false;
        }
    }

}