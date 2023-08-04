package com.dzapicommon.entity.service;


import com.dzapicommon.entity.model.entity.InterfaceInfo;

/**
* @author da zhou
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2023-07-29 14:51:45
*/
public interface InnerInterfaceInfoService {

    //2.从数据库中查询模拟接口，是否存在(请求路径，请求方法，请求参数，布尔)
    InterfaceInfo getInterfaceInfo(String path,String method);


}
