
package com.lzx.spring.cloud.bank1.service.impl;

import com.lzx.spring.cloud.bank1.dao.AccountInfoDao;
import com.lzx.spring.cloud.bank1.feign.Bank2Client;
import com.lzx.spring.cloud.bank1.service.AccountInfoService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.core.context.HmilyContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    private AccountInfoDao accountInfoDao;

    private Bank2Client bank2Client;

    private Long transId;

    @Autowired
    public void setAccountInfoDao(AccountInfoDao accountInfoDao) {
        this.accountInfoDao = accountInfoDao;
    }

    @Autowired
    public void setBank2Client(Bank2Client bank2Client) {
        this.bank2Client = bank2Client;
    }

    /**
     * tcc各方法通过不同的线程调用
     * 2.1.1@Hmily的变更
     * https://blog.csdn.net/weixin_29903713/article/details/112206848
     * 账户扣款,预扣款就是tcc的try方法(T)
     * 1、try幂等校验
     * 2、try悬挂处理
     * 3、检查余额是够扣减金额
     * 4、扣减金额
     * @param accountNo 账户
     * @param amount 扣款金额
     */
    @Override
    @Transactional
    @HmilyTCC(confirmMethod = "commit", cancelMethod = "rollback")//只要标记@Hmily就是try方法，在注解中指定confirm、cancel两个方法名字
    public void updateAccountBalance(String accountNo, Double amount) {
        //1、获取全局事务id，标记@HmilyTCC的方法会产生一个全局事务id(之前版本通过HmilyTransactionContextLocal获取)
        //全局事务id
        transId =  HmilyContextHolder.get().getTransId();

        //2、判断local_try_log表中是否有try日志记录，如果有则不再执行（幂等校验）
        if(accountInfoDao.isExistTry(transId) > 0) {
            System.out.println("bank1 try 已经执行，无需重复执行，xid：" + transId);
            return;
        }

        //3、try悬挂处理，如果cancel、confirm有一个已经执行，try不再执行
        if(accountInfoDao.isExistConfirm(transId) > 0 || accountInfoDao.isExistCancel(transId) > 0) {
            System.out.println("bank1 try 悬挂处理，cancel或confirm已经执行，不允许执行try，xid：" + transId);
            return;
        }

        //4、扣减金额
        if(accountInfoDao.subtracAccountBalance(accountNo, amount) <= 0) {
            //扣减失败
            throw new RuntimeException("bank1 try 扣减金额失败，xid：" + transId);
        }

        //5、插入try执行记录,用于幂等判断
        accountInfoDao.addTry(transId);

        //6、扣款完后，远程调用bank2转账
        if(!bank2Client.transfer(amount)) {
            throw new RuntimeException("bank1 调用bank2微服务失败，xid：" + transId);
        }

    }

    /**
     * 事务发起方转账，confirm无业务操作
     * confirm方法(C)
     * @param accountNo 账户
     * @param amount 扣款金额
     */
    public void commit(String accountNo, Double amount) {
        System.out.println("事务发起方转账，confirm无业务操作" + "账户：" + accountNo + " 转账金额：:" + amount);
    }

    /**
     * 1、幂等校验
     * 2、cancel空回滚处理
     * 3、增加可用金额
     * cancel方法(C)
     * @param accountNo 账户
     * @param amount 扣款金额
     */
    @Transactional
    public void rollback(String accountNo, Double amount) {
        //1、获取全局事务id，标记@HmilyTCC的方法会产生一个全局事务id(之前版本通过HmilyTransactionContextLocal获取)
        //HmilyContextHolder.get().getTransId()

        //2、判断local_try_log表中是否有try日志记录，如果有则不再执行（幂等校验）
        if(accountInfoDao.isExistCancel(transId) > 0) {
            System.out.println("bank1 cancel 已经执行，无需重复执行，xid：" + transId);
            return;
        }

        //3、cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(accountInfoDao.isExistTry(transId) <= 0) {
            System.out.println("bank1 空回滚处理，try没有执行，cancel不允许执行，xid：" + transId);
            return;
        }

        //4、增减可用余额
        accountInfoDao.addAccountBalance(accountNo, amount);

        ////5、插入cancel执行记录,用于幂等判断
        accountInfoDao.addCancel(transId);
    }

}
