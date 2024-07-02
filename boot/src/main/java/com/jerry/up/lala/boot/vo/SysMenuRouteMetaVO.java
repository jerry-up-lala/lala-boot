package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.entity.SysMenu;
import com.jerry.up.lala.framework.core.data.DataFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 菜单树 tree meat
 *
 * @author FMJ
 * @date 2023/11/7 09:45
 */
@Data
@Accessors(chain = true)
public class SysMenuRouteMetaVO {

    /**
     * 访问code，多个用逗号分隔
     */
    @DataFormat(sourceFieldName = SysMenu.Fields.accessCodes, split = ",")
    private List<String> accessCodes;

    /**
     * 是否需要登录鉴权
     */
    private Boolean requiresAuth;

    /**
     * 菜单配置icon
     */
    private String icon;

    /**
     * 名称(语言包键名)
     */
    private String locale;

    /**
     * 是否在左侧菜单中隐藏该项
     */
    private Boolean hideInMenu;

    /**
     * 强制在左侧菜单中显示单项
     */
    private Boolean hideChildrenInMenu;

    /**
     * 排序值
     */
    @DataFormat(sourceFieldName = SysMenu.Fields.menuOrder)
    private Integer order;

    /**
     * 如果设置为true，标签将不会添加到tab-bar中
     */
    private Boolean noAffix;

    private Boolean ignoreCache;

    private List<String> breadcrumb;

}
