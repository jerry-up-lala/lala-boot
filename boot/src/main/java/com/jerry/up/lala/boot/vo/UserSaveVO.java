package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.entity.User;
import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 用户保存
 *
 * @author FMJ
 * @date 2023/12/8 17:22
 */
@Data
@Accessors(chain = true)
@DataBean(targetTypes = User.class)
public class UserSaveVO {

    /**
     * 账号(集团下唯一)
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

    /**
     * 状态[true-启用]
     */
    private Boolean state;

    /**
     * 角色ID列表
     */
    private List<Long> roleIds;

}
