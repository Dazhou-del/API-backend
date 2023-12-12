package com.dazhou.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dazhou.project.service.OrdersService;
import com.dazhou.project.mapper.OrdersMapper;
import com.dzapicommon.entity.service.model.entity.Orders;
import org.springframework.stereotype.Service;

/**
* @author da zhou
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2023-12-06 20:38:33
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




