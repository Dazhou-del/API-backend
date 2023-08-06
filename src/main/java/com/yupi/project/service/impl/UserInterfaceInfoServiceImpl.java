package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dzapicommon.common.ErrorCode;
import com.dzapicommon.constant.CommonConstant;
import com.dzapicommon.entity.service.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.dzapicommon.entity.service.model.entity.UserInterfaceInfo;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import com.yupi.project.service.UserInterfaceInfoService;
import com.yupi.project.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author da zhou
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-08-02 00:37:20
*/
@Service
@DubboService
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;
    /**
     * 参数校验
     *
     * @param userInterfaceInfo
     * @param add
     */
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = userInterfaceInfo.getId();
        //接口id
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        //创建人id
        Long userId = userInterfaceInfo.getUserId();
        // 如果是添加时，所有参数必须非空,否者会抛出参数错误的异常
        if (add) {
            if (interfaceInfoId<=0 || userId<=0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或者用户不存在");
            }
        }
        //本期写成<50(没有解决)，第二期视频中解决了。
        if (userInterfaceInfo.getLeftNum()<0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }

    }


    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        //判断参数是否正确
        if(interfaceInfoId<=0 || userId<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.eq("userId",userId);
        updateWrapper.setSql("leftNum=leftNum-1,totalNum=totalNum+1");
        return this.update(updateWrapper);
    }

    /**
     * 获取查询包装类
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest interfaceInfoQueryRequest) {

        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (interfaceInfoQueryRequest == null) {
            return queryWrapper;
        }

        String searchText = interfaceInfoQueryRequest.getSearchText();
        Long id = interfaceInfoQueryRequest.getId();
        Long userId = interfaceInfoQueryRequest.getUserId();
        Long interfaceInfoId = interfaceInfoQueryRequest.getInterfaceInfoId();
        Integer totalNum = interfaceInfoQueryRequest.getTotalNum();
        Integer leftNum = interfaceInfoQueryRequest.getLeftNum();
        Integer status = interfaceInfoQueryRequest.getStatus();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();

        // 拼接查询条件
        if (StringUtils.isNotBlank(searchText)) {
            queryWrapper.like("name", searchText);
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(totalNum), "totalNum", totalNum);
        queryWrapper.eq(ObjectUtils.isNotEmpty(leftNum), "leftNum", leftNum);
        queryWrapper.eq(ObjectUtils.isNotEmpty(interfaceInfoId), "interfaceInfoId", interfaceInfoId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }


    /**
     * 查询剩余次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public UserInterfaceInfo getLeftNum(long interfaceInfoId, long userId) {
        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInterfaceInfo::getInterfaceInfoId,interfaceInfoId);
        queryWrapper.eq(UserInterfaceInfo::getUserId,userId);
        UserInterfaceInfo userInterfaceInfo = this.getOne(queryWrapper);
        return userInterfaceInfo;
    }

    @Override
    public Page<UserInterfaceInfo> getUserInterfaceInfoVOPage(Page<UserInterfaceInfo> userInterfaceInfoPage, HttpServletRequest request) {
        List<UserInterfaceInfo> interfaceInfoList = userInterfaceInfoPage.getRecords();
        Page<UserInterfaceInfo> interfaceInfoVOPage = new Page<>(userInterfaceInfoPage.getCurrent(), userInterfaceInfoPage.getSize(), userInterfaceInfoPage.getTotal());
        if (CollectionUtils.isEmpty(interfaceInfoList)) {
            return interfaceInfoVOPage;
        }
        interfaceInfoVOPage.setRecords(interfaceInfoList);
        return interfaceInfoVOPage;
    }

    @Override
    public List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit) {
        return userInterfaceInfoMapper.listTopInvokeInterfaceInfo(limit);
    }
}




