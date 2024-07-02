package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.framework.core.common.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 *
 * @author FMJ
 * @TableName role
 */
@TableName(value = "role")
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends Entity {
    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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

}