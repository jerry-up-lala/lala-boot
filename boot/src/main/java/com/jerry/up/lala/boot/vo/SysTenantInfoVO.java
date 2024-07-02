package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>Description: 集团 信息 vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@Accessors(chain = true)
public class SysTenantInfoVO {

    private String id;

    /**
     * 集团名称
     */
    private String tenantName;

    /**
     * 状态[true-启用]
     */
    private Boolean state;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;

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
     * 状态[true-启用]
     */
    private Boolean userState;

}
