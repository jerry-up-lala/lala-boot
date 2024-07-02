package com.jerry.up.lala.boot.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.jerry.up.lala.boot.entity.RoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author jerry
 * @description 针对表【role_user(角色绑定账号表)】的数据库操作Mapper
 * @createDate 2023-12-13 16:33:53
 * @Entity com.jerry.up.lala.boot.entity.RoleUser
 */
public interface RoleUserMapper extends MPJBaseMapper<RoleUser> {

    int insertBatch(@Param("roleUserCollection") Collection<RoleUser> roleUserCollection);
}




