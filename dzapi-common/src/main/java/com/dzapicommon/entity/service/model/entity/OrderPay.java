package com.dzapicommon.entity.service.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单商品表
 * @TableName order_pay
 */
@TableName(value ="order_pay")
@Data
public class OrderPay implements Serializable {
    /**
     * 订单ID
     */

    private String orderId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 支付价格
     */
    private Integer payPrice;

    /**
     * 支付状态（0：未支付，1：已支付）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}