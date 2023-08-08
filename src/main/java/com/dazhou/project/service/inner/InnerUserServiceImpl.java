package com.dazhou.project.service.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dazhou.project.exception.BusinessException;
import com.dazhou.project.mapper.UserMapper;
import com.dzapicommon.common.ErrorCode;

import com.dzapicommon.entity.service.InnerUserService;

import com.dzapicommon.entity.service.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author shkstart
 * @create 2023-08-03 22:12
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;
    //1.数据库中查是否 已经分配给用户密钥(accessKey,secreKey,布尔
    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("accessKey",accessKey);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }
}
