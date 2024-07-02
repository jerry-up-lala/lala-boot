package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.framework.core.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 集团 查询 vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysTenantQueryVO extends PageQuery {

    /**
     * 集团名称
     */
    private String tenantName;

    /**
     * 状态[true-启用]
     */
    private Boolean state;

}
