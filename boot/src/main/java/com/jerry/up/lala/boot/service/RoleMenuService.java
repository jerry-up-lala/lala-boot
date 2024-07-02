package com.jerry.up.lala.boot.service;

import com.github.yulichang.base.MPJBaseService;
import com.jerry.up.lala.boot.entity.RoleMenu;

import java.util.Date;
import java.util.List;

/**
 * @author jerry
 * @description 针对表【role_menu(角色绑定菜单表)】的数据库操作Service
 * @createDate 2023-12-13 16:33:36
 */
public interface RoleMenuService extends MPJBaseService<RoleMenu> {

    void add(List<Long> menuIds, Long roleId, Date createTime, String createUser);

}