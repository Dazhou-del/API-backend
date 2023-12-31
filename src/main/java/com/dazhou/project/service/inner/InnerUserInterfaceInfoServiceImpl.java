package com.dazhou.project.service.inner;


import com.dazhou.project.service.UserInterfaceInfoService;
import com.dzapicommon.entity.service.InnerUserInterfaceInfoService;
import com.dzapicommon.entity.service.model.entity.UserInterfaceInfo;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author shkstart
 * @create 2023-08-03 22:19
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 暴露出去的接口次数+1
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId,userId);
    }

    /**
     * 查询剩余次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public UserInterfaceInfo getLeftNum(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.getLeftNum(interfaceInfoId,userId);
    }

}
