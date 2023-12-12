package com.dzapicommon.entity.service.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品表
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * 商品id
     */
    @TableId
    private Long goods_id;

    /**
     * 商品名称
     */
    private String goods_name;

    /**
     * 图片路径
     */
    private String goods_img;

    /**
     * 商品价格
     */
    private Double goods_price;

    /**
     * 单位
     */
    private String unit;

    /**
     * 库存
     */
    private Integer store;

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