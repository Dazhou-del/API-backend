package com.dzapicommon.entity.service.model.dto.orderpay;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dzapicommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-08 21:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderPayQueryRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    @TableId
    private Long orderId;

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


}
