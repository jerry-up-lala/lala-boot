package com.jerry.up.lala.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jerry.up.lala.boot.entity.SysTenant;
import com.jerry.up.lala.boot.entity.SysUser;
import com.jerry.up.lala.boot.entity.User;
import com.jerry.up.lala.boot.error.BootErrors;
import com.jerry.up.lala.boot.service.*;
import com.jerry.up.lala.boot.vo.LoginUserVO;
import com.jerry.up.lala.boot.vo.LoginVO;
import com.jerry.up.lala.framework.boot.crypto.RSAUtil;
import com.jerry.up.lala.framework.boot.satoken.SaTokenUtil;
import com.jerry.up.lala.framework.boot.tenant.TenantContext;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.model.LoginUser;
import com.jerry.up.lala.framework.common.util.BeanUtil;
import com.jerry.up.lala.framework.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 登录
 *
 * @author FMJ
 * @date 2023/9/4 14:52
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysTenantService sysTenantService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public String sysLogin(LoginVO loginVO) {
        checkArg(loginVO);
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getLoginName, loginVO.getLoginName()));
        if (sysUser == null) {
            throw ServiceException.error(BootErrors.LOGIN_NAME_PASSWORD_INVALID);
        }
        // 判断密码
        if (!RSAUtil.decrypt(sysUser.getPassWord()).equals(loginVO.getPassWord())) {
            throw ServiceException.error(BootErrors.LOGIN_NAME_PASSWORD_INVALID);
        }
        String userId = sysUser.getId();
        return SaTokenUtil.login(userId, new LoginUser().setUserId(userId).setLoginName(sysUser.getLoginName()).setRealName(sysUser.getRealName()));
    }

    @Override
    public String login(LoginVO loginVO) {
        checkArg(loginVO);
        String tenantName = loginVO.getTenantName();
        if (StringUtil.isNull(tenantName)) {
            throw ServiceException.args();
        }
        SysTenant sysTenant = sysTenantService.getOne(new LambdaQueryWrapper<SysTenant>()
                .eq(SysTenant::getTenantName, loginVO.getTenantName()).eq(SysTenant::getState, true));
        if (sysTenant == null) {
            throw ServiceException.error(BootErrors.LOGIN_TENANT_NAME_INVALID);
        }
        TenantContext.setTenantId(sysTenant.getId());
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getLoginName, loginVO.getLoginName()));
        if (user == null) {
            throw ServiceException.error(BootErrors.LOGIN_NAME_PASSWORD_INVALID);
        }
        // 判断密码
        if (!RSAUtil.decrypt(user.getPassWord()).equals(loginVO.getPassWord())) {
            throw ServiceException.error(BootErrors.LOGIN_NAME_PASSWORD_INVALID);
        }
        String userId = user.getId();
        return SaTokenUtil.login(userId, new LoginUser().setUserId(userId).setLoginName(user.getLoginName())
                .setRealName(user.getRealName()).setTenantId(TenantContext.getTenantId()));
    }

    @Override
    public LoginUserVO info() {
        LoginUserVO result = BeanUtil.toBean(SaTokenUtil.currentUser(), LoginUserVO.class);
        List<String> accessCodes = roleService.getAccessCodes();
        return result.setAccessCodes(accessCodes);
    }

    private void checkArg(LoginVO loginVO) {
        String loginName = loginVO.getLoginName();
        String passWord = loginVO.getPassWord();
        if (StringUtil.isNull(loginName) || StringUtil.isNull(passWord)) {
            throw ServiceException.args();
        }
    }
}
