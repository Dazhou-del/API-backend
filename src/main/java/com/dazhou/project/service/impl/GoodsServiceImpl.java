package com.dazhou.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dazhou.project.service.GoodsService;
import com.dazhou.project.mapper.GoodsMapper;
import com.dzapicommon.entity.service.model.entity.Goods;
import org.springframework.stereotype.Service;

/**
* @author da zhou
* @description 针对表【goods(商品表)】的数据库操作Service实现
* @createDate 2023-12-06 21:14:38
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService{

}




