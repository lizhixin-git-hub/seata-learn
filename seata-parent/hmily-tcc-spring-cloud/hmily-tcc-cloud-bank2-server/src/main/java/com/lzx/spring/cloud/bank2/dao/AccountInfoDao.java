package com.lzx.spring.cloud.bank2.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountInfoDao {

    /**
     * 扣款
     * @param accountNo 账户
     * @param amount 扣款金额
     * @return 受影响行数
     */
    @Update("update account_info set account_balance=account_balance-#{amount} where account_balance>=#{amount} and account_no=#{accountNo}")
    int subtracAccountBalance(@Param("accountNo") String accountNo, @Param("amount") Double amount);

    /**
     * 收款
     * @param accountNo 账户
     * @param amount 收款金额
     * @return 受影响行数
     */
    @Update("update account_info set account_balance=account_balance+#{amount} where account_no=#{accountNo}")
    int addAccountBalance(@Param("accountNo") String accountNo, @Param("amount") Double amount);

    /**
     * tcc需注意的：空回滚、幂等、悬挂
     * 增加某分支事务的try执行记录，实现幂等
     * @param localTradeNo 本地事务编号
     * @return 受影响行数
     */
    @Insert("insert into local_try_log values (#{txNo},now())")
    int addTry(Long localTradeNo);

    /**
     * 增加某分支事务的confirm执行记录，实现幂等
     * @param localTradeNo 本地事务编号
     * @return 受影响行数
     */
    @Insert("insert into local_confirm_log values (#{txNo},now())")
    int addConfirm(Long localTradeNo);

    /**
     * 增加某分支事务的cancel执行记录，实现幂等
     * @param localTradeNo 本地事务编号
     * @return 受影响行数
     */
    @Insert("insert into local_cancel_log values (#{txNo},now())")
    int addCancel(Long localTradeNo);

    /**
     * 查询分支事务try是否已执行
     * @param localTradeNo 本地事务编号
     * @return 数据条数
     */
    @Select("select count(1) from local_try_log where tx_no=#{txNo}")
    int isExistTry(Long localTradeNo);

    /**
     * 查询分支事务confirm是否已执行
     * @param localTradeNo 本地事务编号
     * @return 数据条数
     */
    @Select("select count(1) from local_confirm_log where tx_no=#{txNo}")
    int isExistConfirm(Long localTradeNo);

    /**
     * 查询分支事务cancel是否已执行
     * @param localTradeNo 本地事务编号
     * @return 数据条数
     */
    @Select("select count(1) from local_cancel_log where tx_no=#{txNo}")
    int isExistCancel(Long localTradeNo);

}
