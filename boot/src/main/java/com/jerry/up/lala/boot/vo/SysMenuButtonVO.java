package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.entity.SysMenu;
import com.jerry.up.lala.boot.enums.SysMenuType;
import com.jerry.up.lala.framework.core.data.DataFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 菜单 按钮 vo
 *
 * @author FMJ
 * @date 2023/11/7 09:45
 */
@Data
@Accessors(chain = true)
public class SysMenuButtonVO {

    /**
     * 菜单ID/按钮ID
     */
    private Long id;

    /**
     * @see SysMenuType 菜单按钮类型
     */
    private Integer type;

    /**
     * 菜单名称/按钮名称(语言包键名)
     */
    private String locale;

    /**
     * 访问码
     */
    @DataFormat(sourceFieldName = SysMenu.Fields.accessCodes, split = ",")
    private List<String> accessCodes;

    /**
     * 是否需要鉴权
     */
    private Boolean requiresAuth;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 路由名称
     */
    private String name;

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
     * 排序值
     */
    private Integer menuOrder;

    /**
     * 子
     */
    private List<SysMenuButtonVO> children;
}
