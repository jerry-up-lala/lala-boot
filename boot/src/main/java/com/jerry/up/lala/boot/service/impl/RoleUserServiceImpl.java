package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.jerry.up.lala.boot.entity.RoleUser;
import com.jerry.up.lala.boot.mapper.RoleUserMapper;
import com.jerry.up.lala.boot.service.RoleUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【role_user(角色绑定账号表)】的数据库操作Service实现
 * @createDate 2023-12-13 16:33:53
 */
@Service
public class RoleUserServiceImpl extends MPJBaseServiceImpl<RoleUserMapper, RoleUser> implements RoleUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(String userId, List<Long> roleIds, Date createTime, String createUser) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return;
        }
        List<RoleUser> roleUserList = roleIds.stream().map(item -> {
            RoleUser roleUser = new RoleUser().setUserId(userId).setRoleId(item);
            roleUser.setCreateTime(createTime);
            roleUser.setCreateUser(createUser);
            return roleUser;
        }).collect(Collectors.toList());
        getBaseMapper().insertBatch(roleUserList);
    }
}




