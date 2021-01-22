package com.lzx.spring.cloud.bank1.controller;

import com.lzx.spring.cloud.bank1.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank1")
public class Bank1Controller {

    private AccountInfoService accountInfoService;

    @Autowired
    public void setAccountInfoService(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    /**
     * bank1扣款
     * @param amount 转账金额
     * @return 转账结果
     */
    @GetMapping("/transfer")
    public Boolean transfer(@RequestParam("amount") Double amount) {
        accountInfoService.updateAccountBalance("1", amount);
        return true;
    }

}
