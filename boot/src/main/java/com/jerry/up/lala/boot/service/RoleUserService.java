package com.jerry.up.lala.boot.service;

import com.github.yulichang.base.MPJBaseService;
import com.jerry.up.lala.boot.entity.RoleUser;

import java.util.Date;
import java.util.List;

/**
 * @author jerry
 * @description 针对表【role_user(角色绑定账号表)】的数据库操作Service
 * @createDate 2023-12-13 16:33:53
 */
public interface RoleUserService extends MPJBaseService<RoleUser> {

    void add(String userId, List<Long> roleIds, Date createTime, String createUser);

}
