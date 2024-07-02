package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 角色 信息 vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RoleInfoVO extends RoleVO {

    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;

    /**
     * 账号ID列表
     */
    private List<String> userIds;
}
