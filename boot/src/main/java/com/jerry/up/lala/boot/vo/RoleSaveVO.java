package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.dto.RoleDTO;
import com.jerry.up.lala.boot.entity.Role;
import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;

import java.util.List;

/**
 * <p>Description: 角色 保存
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@DataBean(targetTypes = {RoleDTO.class, Role.class})
public class RoleSaveVO {

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

    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;
}
