package com.dzapicommon.entity.service.model.dto.orders;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-06 20:49
 */
@Data
public class orderAddRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long user_id;

    /**
     * 支付状态（0：未支付，1：已支付，2：已取消）
     */
    private Integer state;

    /**
     * 支付时间
     */
    private Date pay_time;

    /**
     * 订单价格
     */
    private String prices;
}
