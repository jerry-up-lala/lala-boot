package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.boot.dto.UserDTO;
import com.jerry.up.lala.boot.vo.UserVO;
import com.jerry.up.lala.framework.core.common.Entity;
import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * 账号表
 *
 * @author FMJ
 * @TableName user
 */
@TableName(value = "user")
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Accessors(chain = true)
@DataBean(targetTypes = {UserDTO.class, UserVO.class})
public class User extends Entity {
    /**
     * 账号ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

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

    private String tenantId;

}