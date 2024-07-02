package com.jerry.up.lala.boot.access;

import cn.hutool.core.util.ArrayUtil;
import com.jerry.up.lala.boot.service.RoleService;
import com.jerry.up.lala.framework.core.api.ApiHandle;
import com.jerry.up.lala.framework.core.common.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Description: 访问控制
 *
 * @author FMJ
 * @date 2023/12/14 10:57
 */
@Slf4j
@Service
public class ApiHandleImpl implements ApiHandle {

    @Autowired
    private RoleService roleService;

    @Override
    public boolean noAccess(Api api) {
        if (api != null) {
            String[] apiAccessCodes = api.accessCodes();
            if (ArrayUtil.isNotEmpty(apiAccessCodes)) {
                List<String> accessCodes = roleService.getAccessCodes();
                return Arrays.stream(apiAccessCodes).noneMatch(accessCodes::contains);
            }
        }
        return false;
    }
}
