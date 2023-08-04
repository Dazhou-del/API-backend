package com.yupi.project.model.vo;

import com.dzapicommon.entity.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shkstart
 * @create 2023-08-04 16:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVo extends InterfaceInfo {

    /**
     * 调用次数
     */
    private Integer  totalNum;

    private static final long serialVersionUID = 1L;
}
