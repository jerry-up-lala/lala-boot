package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.boot.vo.SysMenuButtonVO;
import com.jerry.up.lala.boot.vo.SysMenuRouteMetaVO;
import com.jerry.up.lala.boot.vo.SysMenuRouteVO;
import com.jerry.up.lala.framework.core.common.Entity;
import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

/**
 * 菜单表
 *
 * @author FMJ
 * @TableName sys_menu
 */
@TableName(value = "sys_menu")
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@DataBean(targetTypes = {SysMenuRouteVO.class, SysMenuRouteMetaVO.class, SysMenuButtonVO.class})
public class SysMenu extends Entity {
    /**
     * 菜单/按钮ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 组件名称 必须与组件name一致，否则缓存失效
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