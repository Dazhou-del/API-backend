package com.dazhou.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dazhou.project.service.OrderPayService;
import com.dazhou.project.mapper.OrderPayMapper;
import com.dzapicommon.entity.service.model.entity.OrderPay;
import org.springframework.stereotype.Service;

/**
* @author da zhou
* @description 针对表【order_pay(订单商品表)】的数据库操作Service实现
* @createDate 2023-12-06 21:49:32
*/
@Service
public class OrderPayServiceImpl extends ServiceImpl<OrderPayMapper, OrderPay>
    implements OrderPayService{

}




