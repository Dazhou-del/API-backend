package com.yupi.project.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dzapicommon.common.ErrorCode;
import com.dzapicommon.entity.service.model.entity.InterfaceInfo;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.InterfaceInfoMapper;
import com.yupi.project.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author da zhou
 * @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
 * @createDate 2023-07-29 14:51:45
 */
@Service

public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    /**
     * 参数校验
     *
     * @param interfaceInfo
     * @param add
     */
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = interfaceInfo.getId();
        //接口名称
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        Integer status = interfaceInfo.getStatus();
        String method = interfaceInfo.getMethod();
        //创建人id
        Long userId = interfaceInfo.getUserId();
        Date createTime = interfaceInfo.getCreateTime();
        Date updateTime = interfaceInfo.getUpdateTime();
        Integer isDeleted = interfaceInfo.getIsDelete();
        // 如果是添加时，所有参数必须非空,否者会抛出参数错误的异常
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        //如果接口名称不为空且长度>50,抛出参数错误的异常，错误信息为名称过长
        //本期写成<50(没有解决)，第二期视频中解决了。
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }

    }

}




