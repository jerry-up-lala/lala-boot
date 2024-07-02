package com.jerry.up.lala.boot.dto;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.boot.entity.RoleUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 用户角色ID
 *
 * @author FMJ
 * @date 2023/12/21 09:53
 */
@TableName(value = "role")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleUserDTO extends RoleUser {

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
