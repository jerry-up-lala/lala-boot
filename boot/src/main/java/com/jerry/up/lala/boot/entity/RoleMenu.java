package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.framework.core.common.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * 角色绑定菜单表
 *
 * @author FMJ
 * @TableName role_menu
 */
@TableName(value = "role_menu")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends Entity {
    /**
     * 角色菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 集团ID
     */
    private String tenantId;

    /**
     * role.id
     */
    private Long roleId;

    /**
     * sys_menu.id
     */
    private Long menuId;
}