package com.lzx.spring.cloud.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 订单实体
 */
@TableName("orders")
public class OrderDO {

    /**
     * 订单编号
     **/
    @TableId("id")
    private Integer id;

    /**
     * 用户编号
     **/
    @TableField("user_id")
    private Long userId;

    /**
     * 产品编号
     **/
    @TableField("product_id")
    private Long productId;

    /**
     * 支付金额
     **/
    @TableField("pay_amount")
    private Integer payAmount;

    /**
     * 添加时间
     **/
    @TableField("add_time")
    private LocalDateTime addTime;

    /**
     * 最后更新时间
     **/
    @TableField("last_update_time")
    private LocalDateTime lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public OrderDO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public OrderDO setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public OrderDO setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public OrderDO setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
        return this;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
