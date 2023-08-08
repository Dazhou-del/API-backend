package com.dazhou.project.service.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dazhou.project.exception.BusinessException;
import com.dazhou.project.mapper.InterfaceInfoMapper;
import com.dzapicommon.common.ErrorCode;

import com.dzapicommon.entity.service.InnerInterfaceInfoService;

import com.dzapicommon.entity.service.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author shkstart
 * @create 2023-08-03 22:12
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;
    //2.从数据库中查询模拟接口，是否存在(请求路径，请求方法，请求参数，布尔)
    @Override
    public InterfaceInfo getInterfaceInfo(String host,String url, String method) {
        if (StringUtils.isAnyBlank(host,url,method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<InterfaceInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("host",host);
        queryWrapper.eq("method",method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
        return interfaceInfo;
    }
}
