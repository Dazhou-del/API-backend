package com.dazhou.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dazhou.project.service.OrderGoodService;
import com.dazhou.project.mapper.OrderGoodMapper;
import com.dzapicommon.entity.service.model.entity.OrderGood;
import org.springframework.stereotype.Service;

/**
* @author da zhou
* @description 针对表【order_good(订单商品表)】的数据库操作Service实现
* @createDate 2023-12-06 21:50:01
*/
@Service
public class OrderGoodServiceImpl extends ServiceImpl<OrderGoodMapper, OrderGood>
        implements OrderGoodService{


}




