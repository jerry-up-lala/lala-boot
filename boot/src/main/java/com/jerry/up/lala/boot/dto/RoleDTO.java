package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.entity.Role;
import com.jerry.up.lala.boot.vo.RoleInfoVO;
import com.jerry.up.lala.framework.core.data.DataBean;
import com.jerry.up.lala.framework.core.data.DataFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * 角色
 *
 * @author FMJ
 * @TableName role
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@FieldNameConstants
@DataBean(targetTypes = {RoleInfoVO.class})
public class RoleDTO extends Role {

    private String menuIdStrs;

    @DataFormat(sourceFieldName = Fields.menuIdStrs, split = ",")
    private List<Long> menuIds;

    private String userIdStrs;

    @DataFormat(sourceFieldName = Fields.userIdStrs, split = ",")
    private List<String> userIds;

}