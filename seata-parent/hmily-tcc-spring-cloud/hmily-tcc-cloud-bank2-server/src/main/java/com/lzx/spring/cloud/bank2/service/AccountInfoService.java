package com.lzx.spring.cloud.bank2.service;

public interface AccountInfoService {

    /**
     * 账户加款
     * @param accountNo 账户
     * @param amount 加款金额
     */
    void updateAccountBalance(String accountNo, Double amount);

}
