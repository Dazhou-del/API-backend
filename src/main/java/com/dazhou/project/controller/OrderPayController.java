package com.dazhou.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dazhou.project.constant.CommonConstant;
import com.dazhou.project.exception.BusinessException;
import com.dazhou.project.service.OrderPayService;
import com.dazhou.project.service.UserService;
import com.dzapicommon.common.BaseResponse;
import com.dzapicommon.common.DeleteRequest;
import com.dzapicommon.common.ErrorCode;
import com.dzapicommon.common.ResultUtils;
import com.dzapicommon.entity.service.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.dzapicommon.entity.service.model.dto.orderpay.OrderPayQueryRequest;
import com.dzapicommon.entity.service.model.entity.InterfaceInfo;
import com.dzapicommon.entity.service.model.entity.OrderPay;
import com.dzapicommon.entity.service.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-06 21:56
 */
@RestController
@RequestMapping("/orderpay")
@Slf4j
public class OrderPayController {
    @Resource
    private OrderPayService orderPayService;

    @Resource
    private UserService userService;

    /**
     * 分页获取列表
     *
     * @param orderPayQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<OrderPay>> listOrderPayByPage(OrderPayQueryRequest orderPayQueryRequest, HttpServletRequest request) {
        if (orderPayQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        OrderPay orderPay = new OrderPay();
        BeanUtils.copyProperties(orderPayQueryRequest, orderPay);
        //获取当前页号和页面大小
        long current = orderPayQueryRequest.getCurrent();
        long size = orderPayQueryRequest.getPageSize();
        //排序字段和排序顺序
        String sortField = orderPayQueryRequest.getSortField();
        String sortOrder = orderPayQueryRequest.getSortOrder();
        // description 需支持模糊搜索
//        orderPayQueryRequest.setDescription(null);
        // 限制爬虫
        if (size > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<OrderPay> queryWrapper = new QueryWrapper<>();
        //模糊搜索description
//        queryWrapper.like(StringUtils.isNotBlank(description),"description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<OrderPay> orderPayPage = orderPayService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(orderPayPage);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteOrderPay(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        OrderPay orderPay = orderPayService.getById(id);
        if (orderPay == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!orderPay.getUserName().equals(user.getUserName()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = orderPayService.removeById(id);
        return ResultUtils.success(b);
    }
}
