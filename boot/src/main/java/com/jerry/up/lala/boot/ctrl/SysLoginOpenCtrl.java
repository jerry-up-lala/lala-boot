package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.service.LoginService;
import com.jerry.up.lala.boot.vo.LoginVO;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 登录
 *
 * @author FMJ
 * @date 2023/9/4 14:41
 */
@RestController
@RequestMapping("/open/sys/login")
public class SysLoginOpenCtrl {

    @Autowired
    private LoginService loginService;

    @PostMapping
    @Api(value = "登录-管理员登录")
    public R<String> sysLogin(@RequestBody LoginVO loginVO) {
        String token = loginService.sysLogin(loginVO);
        return R.succeed(token);
    }

}
