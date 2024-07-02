package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.entity.SysTenant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户表
 *
 * @author FMJ
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysTenantDTO extends SysTenant {

    /**
     * 账号ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 状态[true-启用]
     */
    private Boolean userState;

    /**
     * 集团管理员
     */
    private Boolean tenantAdmin;

}