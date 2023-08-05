package com.yupi.project.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dzapicommon.entity.service.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author da zhou
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Mapper
* @createDate 2023-08-02 00:37:20
* @Entity com.yupi.project.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    //查出前3个 调用次数最多的
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




