package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 集团 新增 vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysTenantAddVO extends SysTenantUpdateVO {

    /**
     * 管理员
     */
    private UserSaveVO user;

}
