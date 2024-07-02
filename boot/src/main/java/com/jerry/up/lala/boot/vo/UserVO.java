package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: 账号 信息 vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@Accessors(chain = true)
public class UserVO {

    /**
     * 账号ID
     */
    private String id;

    /**
     * 用户名
     */
    private String loginName;

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
     * 集团管理员
     */
    private Boolean tenantAdmin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    /**
     * 有效角色名称
     */
    private List<String> effectiveRoleNames;

    /**
     * 无效角色名称
     */
    private List<String> invalidRoleNames;

}
