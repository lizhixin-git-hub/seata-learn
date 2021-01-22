
package com.lzx.spring.cloud.bank2.service.impl;

import com.lzx.spring.cloud.bank2.dao.AccountInfoDao;
import com.lzx.spring.cloud.bank2.service.AccountInfoService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.core.context.HmilyContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    private AccountInfoDao accountInfoDao;

    private Long transId;

    @Autowired
    public void setAccountInfoDao(AccountInfoDao accountInfoDao) {
        this.accountInfoDao = accountInfoDao;
    }
    /**
     * tcc各方法通过不同的线程调用
     * 增加金额放在confirm方法中，try中为空方法
     * @param accountNo 账户
     * @param amount 增加金额
     */
    @Override
    @HmilyTCC(confirmMethod = "commit", cancelMethod = "rollback")//只要标记@Hmily就是try方法，在注解中指定confirm、cancel两个方法名字
    public void updateAccountBalance(String accountNo, Double amount) {
        //1、获取全局事务id，标记@HmilyTCC的方法会产生一个全局事务id(之前版本通过HmilyTransactionContextLocal获取)
        transId =  HmilyContextHolder.get().getTransId();;

        System.out.println("接收转账try，全局事务id：" + transId + "账号：" + accountNo + " 转款金额：" + amount);
    }

    /**
     * 1、幂等性校验
     * 2、正式增加金额
     * confirm方法(C)
     * @param accountNo 账户
     * @param amount 增加金额
     */
    @Transactional
    public void commit(String accountNo, Double amount) {
        //1、获取全局事务id，标记@HmilyTCC的方法会产生一个全局事务id(之前版本通过HmilyTransactionContextLocal获取)
        //Long transId = HmilyContextHolder.get().getTransId();

        //2、判断local_try_log表中是否有try日志记录，如果有则不再执行（幂等校验）
        if(accountInfoDao.isExistConfirm(transId) > 0) {
            System.out.println("bank2 confirm 已经执行，无需重复执行，xid：" + transId);
            return;
        }

        //3、增减可用余额
        accountInfoDao.addAccountBalance(accountNo, amount);

        //5、插入confirm执行记录,用于幂等判断
        accountInfoDao.addConfirm(transId);
    }

    /**
     * cancel方法(C)
     * @param accountNo 账户
     * @param amount 增加金额
     */
    public void rollback(String accountNo, Double amount) {
        //1、获取全局事务id，标记@HmilyTCC的方法会产生一个全局事务id(之前版本通过HmilyTransactionContextLocal获取)
        //Long transId = HmilyContextHolder.get().getTransId();

        System.out.println("接收转账cancel，全局事务id" + transId + "账号：" + accountNo + " 转款金额：" + amount);
    }

}
