package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 菜单路由树 tree
 *
 * @author FMJ
 * @date 2023/11/7 09:45
 */
@Data
@Accessors(chain = true)
public class SysMenuRouteVO {

    private Long id;

    /**
     * 菜单父ID
     */
    private Long parentId;

    /**
     * 菜单地址
     */
    private String path;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单原信息
     */
    private SysMenuRouteMetaVO meta;

    /**
     * 子
     */
    private List<SysMenuRouteVO> children;
}
