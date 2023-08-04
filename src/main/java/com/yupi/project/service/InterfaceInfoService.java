package com.yupi.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dzapicommon.entity.model.entity.InterfaceInfo;

/**
* @author da zhou
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2023-07-29 14:51:45
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验参数
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
