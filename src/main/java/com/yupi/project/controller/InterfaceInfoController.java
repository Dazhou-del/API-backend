package com.yupi.project.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dazhou.dazhouclientsdk.client.RzApiClient;
import com.dzapicommon.common.*;
import com.dzapicommon.entity.service.InnerUserInterfaceInfoService;
import com.dzapicommon.entity.service.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.dzapicommon.entity.service.model.dto.interfaceinfo.InterfaceInfoExecuteRequest;
import com.dzapicommon.entity.service.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.dzapicommon.entity.service.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.dzapicommon.entity.service.model.entity.InterfaceInfo;
import com.dzapicommon.entity.service.model.entity.User;
import com.dzapicommon.entity.service.model.enums.InterfaceInfoStatusEnum;
import com.dzapicommon.entity.service.model.vo.InterfaceInfoVO;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.constant.CommonConstant;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.exception.ThrowUtils;
import com.yupi.project.service.InterfaceInfoService;
import com.yupi.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 接口管理
 *
 * @author dazhou
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;
    @Resource
    private RzApiClient rzApiClient;

    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        //获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        interfaceInfo.setRequestParamsRemark(JSONUtil.toJsonStr(interfaceInfoAddRequest.getRequestParamsRemark()));
        interfaceInfo.setResponseParamsRemark(JSONUtil.toJsonStr(interfaceInfoAddRequest.getResponseParamsRemark()));
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        //保存之后 InterfaceInfo就已经分配好id了
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param interfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = interfaceInfoService.updateInterfaceInfo(interfaceInfoUpdateRequest);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id 接口id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<InterfaceInfoVO> getInterfaceInfoVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVO(interfaceInfo, request));
    }


    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        //获取当前页号和页面大小
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        //排序字段和排序顺序
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
      QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        //模糊搜索description

        queryWrapper.like(StringUtils.isNotBlank(description),"description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }



    /**
     * 分页获取列表（封装类）
     *
     * @param interfaceInfoQueryRequest 查询条件
     * @param request                   请求
     * @return 分页列表
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoVOByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                         HttpServletRequest request) {
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        interfaceInfoQueryRequest.setSortField("createTime");
        // 倒序排序
        interfaceInfoQueryRequest.setSortOrder(CommonConstant.SORT_ORDER_DESC);
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVOPage(interfaceInfoPage, request));
    }

    /**
     * 根据 当前用户ID 分页获取列表（封装类）
     *
     * @param interfaceInfoQueryRequest 查询条件
     * @param request                   请求
     * @return 分页列表
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoVOByUserIdPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                               HttpServletRequest request) {
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        interfaceInfoQueryRequest.setSortField("createTime");
        // 倒序排序
        interfaceInfoQueryRequest.setSortOrder(CommonConstant.SORT_ORDER_DESC);
        // 限制爬虫
        ThrowUtils.throwIf(size > 30, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVOByUserIdPage(interfaceInfoPage, request));
    }

    // endregion

    /**
     * 发布
     *只有管理才能操作
     * @param interfaceInfoInvokeRequest
     * @param request
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody InterfaceInfoExecuteRequest interfaceInfoInvokeRequest,
                                                     HttpServletRequest request) {
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断接口是否存在
        Long id = interfaceInfoInvokeRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if(oldInterfaceInfo==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口不存在");
        }

        String requestParams = interfaceInfoInvokeRequest.getRequestParams();
        // 接口请求地址
        String url = oldInterfaceInfo.getUrl();
        String method = oldInterfaceInfo.getMethod();
        String host = oldInterfaceInfo.getHost();
        // 获取SDK客户端
        //查询用户的accessKey,secretKey
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        RzApiClient rzApiClient = new RzApiClient(accessKey, secretKey);
        // 设置网关地址
        rzApiClient.setGatewayHost(host);
        // 判断是否可以调用
        try {
            // 执行方法
            String invokeResult = rzApiClient.executeApi(requestParams, url, method);
            if (StringUtils.isBlank(invokeResult)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口数据为空");
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口验证失败");
        }

        // 修改接口状态为 上线状态
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);


     
    }
    /**
     * 下线
     * 只有管理才能操作
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        //判断参数是否存在
        if(idRequest==null ||idRequest.getId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断接口是否存在
        long id = idRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //修改状态
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 参数调用
     * 只有管理才能操作
     * @param interfaceInfoInvokeRequest
     * @param request
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoExecuteRequest interfaceInfoInvokeRequest,
                                                      HttpServletRequest request) {
        //判断参数是否存在
        if(interfaceInfoInvokeRequest==null ||interfaceInfoInvokeRequest.getId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断接口是否存在
        long id = interfaceInfoInvokeRequest.getId();
        String userRequestParams = interfaceInfoInvokeRequest.getRequestParams();
        //判断这个id的数据是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        //从数据库中查询出host,url,method
        String host = oldInterfaceInfo.getHost();
        String url = oldInterfaceInfo.getUrl();
        String method = oldInterfaceInfo.getMethod();
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //判断状态
        if(oldInterfaceInfo.getStatus() ==InterfaceInfoStatusEnum.OFFLINE.getValue()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口已经关闭");
        }
        //查询用户的accessKey,secretKey
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        RzApiClient tempClient = new RzApiClient(accessKey, secretKey);
        tempClient.setGatewayHost(host);
        //调用客户端
        String result = tempClient.executeApi(userRequestParams,url,method);
        if (result==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口参数不对调用失败");
        }
        //不经过网关的在这里调用次数就+1
        if (!host.equals("http://localhost:8090")){
            innerUserInterfaceInfoService.invokeCount(oldInterfaceInfo.getId(),loginUser.getId());
        }
        return ResultUtils.success(result);
    }


}
