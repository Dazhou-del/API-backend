package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dzapicommon.entity.model.entity.UserInterfaceInfo;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import com.yupi.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
* @author da zhou
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-08-02 00:37:20
*/
@Service
@DubboService
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {


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
        Integer status = userInterfaceInfo.getStatus();
        //创建人id
        Long userId = userInterfaceInfo.getUserId();
        Integer isDeleted = userInterfaceInfo.getIsDeleted();
        // 如果是添加时，所有参数必须非空,否者会抛出参数错误的异常
        if (add) {
            if (interfaceInfoId<=0 || userId<=0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或者用户不存在");
            }
        }
        //如果接口名称不为空且长度>50,抛出参数错误的异常，错误信息为名称过长
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

}




