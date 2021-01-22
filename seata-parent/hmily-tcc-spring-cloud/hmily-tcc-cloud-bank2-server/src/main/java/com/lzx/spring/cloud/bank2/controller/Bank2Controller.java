package com.lzx.spring.cloud.bank2.controller;

import com.lzx.spring.cloud.bank2.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank2")
public class Bank2Controller {

    private AccountInfoService accountInfoService;

    @Autowired
    public void setAccountInfoService(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    /**
     * bank2接收转账
     * @param amount 转账金额
     * @return 转账结果
     */
    @GetMapping("/transfer")
    public Boolean transfer(@RequestParam("amount") Double amount) {
        accountInfoService.updateAccountBalance("2", amount);
        return true;
    }

}
