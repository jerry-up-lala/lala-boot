package com.jerry.up.lala.boot.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.jerry.up.lala.boot.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author jerry
 * @description 针对表【role_menu(角色绑定菜单表)】的数据库操作Mapper
 * @createDate 2023-12-13 16:33:36
 * @Entity com.jerry.up.lala.boot.entity.RoleMenu
 */
public interface RoleMenuMapper extends MPJBaseMapper<RoleMenu> {

    int insertBatch(@Param("roleMenuCollection") Collection<RoleMenu> roleMenuCollection);

}




