package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.access.AccessRoleConstant;
import com.jerry.up.lala.boot.access.AccessUserConstant;
import com.jerry.up.lala.boot.service.RoleService;
import com.jerry.up.lala.boot.vo.RoleInfoVO;
import com.jerry.up.lala.boot.vo.RoleQueryVO;
import com.jerry.up.lala.boot.vo.RoleSaveVO;
import com.jerry.up.lala.boot.vo.RoleVO;
import com.jerry.up.lala.framework.core.common.Api;
import com.jerry.up.lala.framework.core.common.DataIdBody;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: 角色管理
 *
 * @author FMJ
 * @date 2023/12/12 11:26
 */
@RestController
@RequestMapping("/role")
public class RoleCtrl {

    @Autowired
    private RoleService roleService;

    @GetMapping("/page")
    @Api(value = "角色管理-查询", accessCodes = {AccessRoleConstant.ROLE, AccessRoleConstant.ROLE_QUERY})
    public R<PageR<RoleVO>> page(RoleQueryVO roleQueryVO) {
        PageR<RoleVO> pageR = roleService.pageQuery(roleQueryVO);
        return R.succeed(pageR);
    }

    @GetMapping("/list")
    @Api(value = "公共接口-角色列表", accessCodes = {AccessUserConstant.USER_ADD, AccessUserConstant.USER_UPDATE})
    public R<List<RoleVO>> listQuery(RoleQueryVO roleQueryVO) {
        List<RoleVO> list = roleService.listQuery(roleQueryVO);
        return R.succeed(list);
    }

    @GetMapping("/info/{id}")
    @Api(value = "角色管理-详情", accessCodes = {AccessRoleConstant.ROLE_INFO, AccessRoleConstant.ROLE_ADD, AccessRoleConstant.ROLE_UPDATE})
    public R<RoleInfoVO> info(@PathVariable("id") Long id) {
        RoleInfoVO roleInfoVO = roleService.info(id);
        return R.succeed(roleInfoVO);
    }

    @GetMapping("/verify")
    @Api(value = "角色管理-校验角色名", accessCodes = {AccessRoleConstant.ROLE_ADD, AccessRoleConstant.ROLE_UPDATE})
    public R verify(DataIdBody<Long, String> dataIdBody) {
        roleService.verify(dataIdBody);
        return R.empty();
    }

    @PostMapping
    @Api(value = "角色管理-新增", accessCodes = AccessRoleConstant.ROLE_ADD)
    public R add(@RequestBody RoleSaveVO roleSaveVO) {
        roleService.add(roleSaveVO);
        return R.empty();
    }

    @PutMapping("/{id}")
    @Api(value = "角色管理-编辑", accessCodes = AccessRoleConstant.ROLE_UPDATE)
    public R put(@PathVariable("id") Long id, @RequestBody RoleSaveVO roleSaveVO) {
        roleService.update(id, roleSaveVO);
        return R.empty();
    }

}
