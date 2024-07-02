package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 个人信息保存 vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@Accessors(chain = true)
public class UserPersonalSaveVO {

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 个人简介
     */
    private String intro;

}
