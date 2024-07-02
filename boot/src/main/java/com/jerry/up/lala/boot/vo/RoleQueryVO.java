package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.dto.RoleQueryDTO;
import com.jerry.up.lala.framework.core.common.PageQuery;
import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * <p>Description: 角色查询
 *
 * @author FMJ
 * @date 2023/12/8 17:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@DataBean(targetTypes = RoleQueryDTO.class)
public class RoleQueryVO extends PageQuery {

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


    private List<String> createTimeRang;

}
