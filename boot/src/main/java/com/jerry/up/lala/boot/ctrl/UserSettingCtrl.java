package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.service.UserSettingService;
import com.jerry.up.lala.boot.vo.UserSettingVO;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: 用户配置
 *
 * @author FMJ
 * @date 2024/1/8 13:43
 */
@RestController
@RequestMapping("/user-setting")
public class UserSettingCtrl {

    @Autowired
    private UserSettingService userSettingService;

    @GetMapping
    @Api(value = "用户配置-查询")
    public R<UserSettingVO> info() {
        UserSettingVO result = userSettingService.info();
        return R.succeed(result);
    }

    @PostMapping
    @Api(value = "用户配置-保存")
    public R save(@RequestBody UserSettingVO userSettingVO) {
        userSettingService.save(userSettingVO);
        return R.empty();
    }
}
