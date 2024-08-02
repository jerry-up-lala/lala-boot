package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.entity.UserSetting;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;

/**
 * 账号配置表
 *
 * @author FMJ
 * @TableName user_setting
 */
@Data
@DataBean(targetTypes = {UserSetting.class})
public class UserSettingVO {

    /**
     * 主题(light/dark)
     */
    private String theme;

    /**
     * 导航栏
     */
    private Boolean navbar;

    /**
     * 菜单栏
     */
    private Boolean menu;

    /**
     * 顶部菜单栏
     */
    private Boolean topMenu;

    /**
     * 菜单折叠
     */
    private Boolean menuCollapse;

    /**
     * 菜单宽度(px)
     */
    private Integer menuWidth;

    /**
     * 底部
     */
    private Boolean footer;

    /**
     * 多页签
     */
    private Boolean tabBar;

    /**
     * 色弱模式
     */
    private Boolean colorWeak;

}