package com.yupi.project.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dzapicommon.entity.service.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.dzapicommon.entity.service.model.entity.UserInterfaceInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    //查询剩余调用次数
    public UserInterfaceInfo getLeftNum(long interfaceInfoId, long userId);

    /**
     * 获取查询条件
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest interfaceInfoQueryRequest);

    /**
     * 分页获取接口信息封装
     *
     * @param userInterfaceInfoPage
     * @param request
     * @return
     */
    Page<UserInterfaceInfo> getUserInterfaceInfoVOPage(Page<UserInterfaceInfo> userInterfaceInfoPage, HttpServletRequest request);


    /**
     * 获取接口调用排名前 n 的接口信息
     *
     * @param limit 前几名
     * @return List<InterfaceInfoVO>
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}

