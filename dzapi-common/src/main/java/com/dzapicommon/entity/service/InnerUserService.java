package com.dzapicommon.entity.service;


import com.dzapicommon.entity.service.model.entity.User;

/**
 * 用户服务
 *
 * @author yupi
 */
public interface InnerUserService  {

    //1.数据库中查是否 已经分配给用户密钥(accessKey,secreKey,布尔
    User getInvokeUser(String accessKey);



}
