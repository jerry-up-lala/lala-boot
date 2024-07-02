package com.jerry.up.lala.boot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 菜单按钮类型
 *
 * @author FMJ
 * @date 2023/12/14 20:26
 */
@Getter
@AllArgsConstructor
public enum SysMenuType {

    /**
     * 菜单-1 按钮-2
     */
    MENU(1),

    BUTTON(2);

    private final Integer value;
}
