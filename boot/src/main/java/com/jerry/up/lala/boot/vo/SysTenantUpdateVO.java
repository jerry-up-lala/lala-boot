package com.jerry.up.lala.boot.vo;

import lombok.Data;

/**
 * <p>Description: 集团 编辑 vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
public class SysTenantUpdateVO {

    /**
     * 集团名称
     */
    private String tenantName;

    /**
     * 状态[true-启用]
     */
    private Boolean state;
}
