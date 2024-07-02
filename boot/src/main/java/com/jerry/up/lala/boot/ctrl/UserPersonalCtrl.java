package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.service.UserService;
import com.jerry.up.lala.boot.vo.UserPersonalPassWordVO;
import com.jerry.up.lala.boot.vo.UserPersonalSaveVO;
import com.jerry.up.lala.boot.vo.UserPersonalVO;
import com.jerry.up.lala.framework.core.common.Api;
import com.jerry.up.lala.framework.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: 个人信息
 *
 * @author FMJ
 * @date 2023/12/8 17:18
 */
@RestController
@RequestMapping("/user-personal")
public class UserPersonalCtrl {

    @Autowired
    private UserService userService;

    @GetMapping
    @Api(value = "个人信息-详情")
    public R<UserPersonalVO> personalInfo() {
        UserPersonalVO result = userService.personalInfo();
        return R.succeed(result);
    }

    @PutMapping
    @Api(value = "个人信息-编辑")
    public R personalSave(@RequestBody UserPersonalSaveVO userPersonalSaveVO) {
        userService.personalSave(userPersonalSaveVO);
        return R.empty();
    }

    @PutMapping("/password")
    @Api(value = "个人信息-更新密码")
    public R personalUpdatePassWord(@RequestBody UserPersonalPassWordVO userPersonalPassWordVO) {
        userService.personalUpdatePassWord(userPersonalPassWordVO);
        return R.empty();
    }


}
