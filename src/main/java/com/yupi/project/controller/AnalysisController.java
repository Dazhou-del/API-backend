package com.yupi.project.controller;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.dzapicommon.common.BaseResponse;
import com.dzapicommon.common.ErrorCode;
import com.dzapicommon.common.ResultUtils;
import com.dzapicommon.entity.service.model.entity.InterfaceInfo;
import com.dzapicommon.entity.service.model.entity.UserInterfaceInfo;
import com.dzapicommon.entity.service.model.vo.InterfaceInfoVO;
import com.yupi.project.annotation.AuthCheck;

import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserInterfaceInfoMapper;

import com.yupi.project.service.InterfaceInfoService;
import com.yupi.project.service.UserInterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分析控制器
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        // 查询调用次数前3名的接口
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoService.listTopInvokeInterfaceInfo(3);
        if (userInterfaceInfoList.isEmpty()) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口信息不存在");
        }
        // 根据接口id分组
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        // 查询所有接口id的接口信息
        List<InterfaceInfo> list = interfaceInfoService.lambdaQuery()
                .in(InterfaceInfo::getId, interfaceInfoIdObjMap.keySet())
                .list();
        if (list.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口信息不存在");
        }
        // 组装返回结果
        List<InterfaceInfoVO> result = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = InterfaceInfoVO.objToVo(interfaceInfo);
            interfaceInfoVO.setTotalNum(interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum());
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(result);
    }

}

