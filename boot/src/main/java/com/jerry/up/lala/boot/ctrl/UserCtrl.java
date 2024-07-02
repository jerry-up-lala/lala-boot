package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.access.AccessUserConstant;
import com.jerry.up.lala.boot.access.AccessNoticeConstant;
import com.jerry.up.lala.boot.service.UserService;
import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.core.common.Api;
import com.jerry.up.lala.framework.core.common.DataIdBody;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: 后台账户管理
 *
 * @author FMJ
 * @date 2023/12/8 17:18
 */
@RestController
@RequestMapping("/user")
public class UserCtrl {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @Api(value = "公共接口-用户列表", accessCodes = {AccessNoticeConstant.NOTICE_ADD, AccessNoticeConstant.NOTICE_UPDATE})
    public R<List<UserVO>> list(UserQueryVO userQueryVO) {
        List<UserVO> list = userService.list(userQueryVO);
        return R.succeed(list);
    }

    @GetMapping("/page")
    @Api(value = "用户管理-查询", accessCodes = {AccessUserConstant.USER, AccessUserConstant.USER_QUERY})
    public R<PageR<UserVO>> page(UserQueryVO userQueryVO) {
        PageR<UserVO> pageR = userService.pageQuery(userQueryVO);
        return R.succeed(pageR);
    }

    @GetMapping("/info/{id}")
    @Api(value = "用户管理-详情", accessCodes = {AccessUserConstant.USER_INFO, AccessUserConstant.USER_UPDATE})
    public R<UserInfoVO> info(@PathVariable("id") String id) {
        UserInfoVO userVO = userService.info(id);
        return R.succeed(userVO);
    }

    @GetMapping("/password/{id}")
    @Api(value = "用户管理-复制密码", accessCodes = AccessUserConstant.USER_PASSWORD)
    public R<String> password(@PathVariable("id") String id) {
        String result = userService.password(id);
        return R.succeed(result);
    }

    @GetMapping("/verify")
    @Api(value = "用户管理-校验用户名", accessCodes = {AccessUserConstant.USER_ADD, AccessUserConstant.USER_UPDATE})
    public R verify(DataIdBody<String, String> dataIdBody) {
        userService.verify(dataIdBody);
        return R.empty();
    }

    @PostMapping
    @Api(value = "用户管理-新增", accessCodes = AccessUserConstant.USER_ADD)
    public R add(@RequestBody UserSaveVO userSaveVO) {
        userService.add(userSaveVO);
        return R.empty();
    }

    @PutMapping("/{id}")
    @Api(value = "用户管理-编辑", accessCodes = AccessUserConstant.USER_UPDATE)
    public R put(@PathVariable("id") String id, @RequestBody UserSaveVO userSaveVO) {
        userService.update(id, userSaveVO);
        return R.empty();
    }

    @PostMapping("/state")
    @Api(value = "用户管理-批量操作状态", accessCodes = AccessUserConstant.USER_STATE_ACTIVATION)
    public R state(@RequestBody UserStateVO userStateVO) {
        userService.state(userStateVO);
        return R.empty();
    }
}
