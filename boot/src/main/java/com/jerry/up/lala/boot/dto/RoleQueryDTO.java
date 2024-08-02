package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.vo.RoleQueryVO;
import com.jerry.up.lala.framework.common.annotation.DataFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>Description: 角色查询
 *
 * @author FMJ
 * @date 2023/12/8 17:22
 */
@Data
@Accessors(chain = true)
public class RoleQueryDTO {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态[true-启用]
     */
    private Boolean state;

    @DataFormat(sourceFieldName = RoleQueryVO.Fields.createTimeRang, listIndex = 0, dateType = 2)
    private Date createTimeStart;

    @DataFormat(sourceFieldName = RoleQueryVO.Fields.createTimeRang, listIndex = 1, dateType = 2)
    private Date createTimeEnd;

}
