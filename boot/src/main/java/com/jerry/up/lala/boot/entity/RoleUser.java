package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.framework.core.common.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色绑定账号表
 *
 * @author FMJ
 * @TableName role_user
 */
@TableName(value = "role_user")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RoleUser extends Entity {
    /**
     * 角色账号ID
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
     * user.id
     */
    private String userId;

}