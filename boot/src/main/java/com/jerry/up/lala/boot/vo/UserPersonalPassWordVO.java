package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 个人信息 更新密码vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@Accessors(chain = true)
public class UserPersonalPassWordVO {

    private String oldPassWord;

    private String newPassWord;

}
