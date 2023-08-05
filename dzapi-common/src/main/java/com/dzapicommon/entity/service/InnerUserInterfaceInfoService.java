package com.dzapicommon.entity.service;


import com.dzapicommon.entity.service.model.entity.UserInterfaceInfo;

/**
* @author da zhou
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2023-08-02 00:37:20
*/
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId,long userId);

    /**
     * 查询剩余次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    UserInterfaceInfo getLeftNum(long interfaceInfoId, long userId);

}
