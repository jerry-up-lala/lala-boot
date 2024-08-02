package com.jerry.up.lala.boot.ctrl;

import cn.dev33.satoken.stp.StpUtil;
import com.jerry.up.lala.boot.service.LoginService;
import com.jerry.up.lala.boot.vo.LoginUserVO;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 登录
 *
 * @author FMJ
 * @date 2023/9/4 14:41
 */
@RestController
@RequestMapping("/login")
public class LoginCtrl {

    @Autowired
    private LoginService loginService;

    @GetMapping
    @Api(value = "公共接口-登录信息", log = false)
    public R<LoginUserVO> info() {
        LoginUserVO result = loginService.info();
        return R.succeed(result);
    }

    @DeleteMapping
    @Api(value = "公共接口-注销", log = false)
    public R logout() {
        StpUtil.logout();
        return R.empty();
    }
}
