package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.jerry.up.lala.boot.entity.RoleMenu;
import com.jerry.up.lala.boot.mapper.RoleMenuMapper;
import com.jerry.up.lala.boot.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【role_menu(角色绑定菜单表)】的数据库操作Service实现
 * @createDate 2023-12-13 16:33:36
 */
@Service
public class RoleMenuServiceImpl extends MPJBaseServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public void add(List<Long> menuIds, Long roleId, Date createTime, String createUser) {
        if (CollectionUtil.isEmpty(menuIds)) {
            return;
        }
        List<RoleMenu> roleMenuList = menuIds.stream().map(item -> {
            RoleMenu roleMenu = new RoleMenu().setRoleId(roleId).setMenuId(item);
            roleMenu.setCreateTime(createTime);
            roleMenu.setCreateUser(createUser);
            return roleMenu;
        }).collect(Collectors.toList());
        getBaseMapper().insertBatch(roleMenuList);
    }
}




