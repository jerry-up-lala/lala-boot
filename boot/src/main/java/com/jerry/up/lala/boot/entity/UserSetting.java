package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.boot.vo.UserSettingVO;
import com.jerry.up.lala.framework.core.common.Entity;
import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 账号配置表
 *
 * @author FMJ
 * @TableName user_setting
 */
@TableName(value = "user_setting")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@DataBean(targetTypes = {UserSettingVO.class})
public class UserSetting extends Entity {
    /**
     * 配置ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 账号ID【user.id】
     */
    private String userId;

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