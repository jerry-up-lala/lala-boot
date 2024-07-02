package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>Description: 角色 信息 vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@Accessors(chain = true)
public class RoleVO {

    /**
     * 角色ID
     */
    private Long id;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;

}
