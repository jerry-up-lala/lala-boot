package com.jerry.up.lala.boot.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>Description: 登录表单
 *
 * @author FMJ
 * @date 2023/9/4 14:55
 */
@Data
public class LoginVO {

    private String loginName;

    private String passWord;

    private String tenantName;

}
