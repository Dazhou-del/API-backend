package com.yupi.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dzapicommon.entity.model.entity.UserInterfaceInfo;

/**
 * @author shkstart
 * @create 2023-08-03 22:22
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {


    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
