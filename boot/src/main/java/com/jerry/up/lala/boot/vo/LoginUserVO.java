package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 登录用户VO
 *
 * @author FMJ
 * @date 2023/12/7 11:17
 */
@Data
@Accessors(chain = true)
public class LoginUserVO {

    /**
     * 账号ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 集团ID
     */
    private String tenantId;

    /**
     * 权限ID列表
     */
    private List<String> accessCodes;
}
