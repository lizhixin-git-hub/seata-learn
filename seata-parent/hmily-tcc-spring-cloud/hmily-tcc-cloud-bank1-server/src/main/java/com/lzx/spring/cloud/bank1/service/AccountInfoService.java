package com.lzx.spring.cloud.bank1.service;

public interface AccountInfoService {

    /**
     * 账户扣款
     * @param accountNo 账户
     * @param amount 扣款金额
     */
    void updateAccountBalance(String accountNo, Double amount);

}
