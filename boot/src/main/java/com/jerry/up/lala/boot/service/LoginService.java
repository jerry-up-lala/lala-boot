package com.jerry.up.lala.boot.service;

import com.jerry.up.lala.boot.vo.LoginUserVO;
import com.jerry.up.lala.boot.vo.LoginVO;
import com.jerry.up.lala.framework.core.common.LoginUser;

/**
 * <p>Description: 登录
 *
 * @author FMJ
 * @date 2023/9/4 14:51
 */
public interface LoginService {

    String sysLogin(LoginVO loginVO);

    String login(LoginVO loginVO);

    LoginUserVO info();
}
