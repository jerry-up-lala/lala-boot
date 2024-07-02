package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.entity.RoleMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>Description: 角色菜单DTO
 *
 * @author FMJ
 * @date 2023/12/21 09:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RoleMenuDTO extends RoleMenu {

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 类型(1-菜单/2-按钮)
     */
    private Long type;

    /**
     * 名称(语言包键名)
     */
    private String locale;

    /**
     * 名称(中文)
     */
    private String localeZhCn;

    /**
     * 是否需要登录鉴权
     */
    private Boolean requiresAuth;

    /**
     * 访问code，多个用逗号分隔
     */
    private String accessCodes;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 菜单配置icon
     */
    private String icon;

    /**
     * 是否在左侧菜单中隐藏该项
     */
    private Boolean hideInMenu;

    /**
     * 强制在左侧菜单中显示单项
     */
    private Boolean hideChildrenInMenu;

    /**
     * 如果设置为true，标签将不会添加到tab-bar中
     */
    private Boolean noAffix;

    /**
     * 排序号(值越高，越靠后)
     */
    private Integer menuOrder;
}
