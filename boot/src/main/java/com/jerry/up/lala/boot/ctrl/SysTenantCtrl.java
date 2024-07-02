package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.access.AccessLogRequestConstant;
import com.jerry.up.lala.boot.access.AccessTenantConstant;
import com.jerry.up.lala.boot.service.SysTenantService;
import com.jerry.up.lala.boot.vo.SysSysTenantAddVO;
import com.jerry.up.lala.boot.vo.SysTenantInfoVO;
import com.jerry.up.lala.boot.vo.SysTenantQueryVO;
import com.jerry.up.lala.boot.vo.SysTenantUpdateVO;
import com.jerry.up.lala.framework.core.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: 集团
 *
 * @author FMJ
 * @date 2023/9/4 14:41
 */
@RestController
@RequestMapping("/sys/tenant")
public class SysTenantCtrl {

    @Autowired
    private SysTenantService sysTenantService;

    @GetMapping("/list")
    @Api(value = "公共接口-集团列表", accessCodes = {AccessLogRequestConstant.LOG_REQUEST, AccessLogRequestConstant.LOG_REQUEST_QUERY})
    public R<List<SysTenantInfoVO>> query(SysTenantQueryVO sysTenantQueryVO) {
        List<SysTenantInfoVO> list = sysTenantService.listQuery(sysTenantQueryVO);
        return R.succeed(list);
    }

    @GetMapping("/page")
    @Api(value = "集团管理-查询", accessCodes = {AccessTenantConstant.TENANT})
    public R<PageR<SysTenantInfoVO>> page(SysTenantQueryVO sysTenantQueryVO) {
        PageR<SysTenantInfoVO> list = sysTenantService.pageQuery(sysTenantQueryVO);
        return R.succeed(list);
    }

    @GetMapping("/info/{id}")
    @Api(value = "集团管理-详情", accessCodes = {AccessTenantConstant.TENANT})
    public R<SysTenantInfoVO> info(@PathVariable("id") String id) {
        SysTenantInfoVO sysTenantInfoVO = sysTenantService.info(id);
        return R.succeed(sysTenantInfoVO);
    }

    @GetMapping("/password/{id}")
    @Api(value = "集团管理-复制管理员密码", accessCodes = AccessTenantConstant.TENANT)
    public R<String> password(@PathVariable("id") String id) {
        String password = sysTenantService.password(id);
        return R.succeed(password);
    }

    @GetMapping("/verify")
    @Api(value = "集团管理-校验集团名称", accessCodes = {AccessTenantConstant.TENANT})
    public R verify(DataIdBody<String, String> dataIdBody) {
        sysTenantService.verify(dataIdBody);
        return R.empty();
    }

    @PostMapping
    @Api(value = "集团管理-新增", accessCodes = AccessTenantConstant.TENANT)
    public R add(@RequestBody SysSysTenantAddVO sysTenantAddVO) {
        sysTenantService.add(sysTenantAddVO);
        return R.empty();
    }

    @PutMapping("/{id}")
    @Api(value = "集团管理-编辑", accessCodes = AccessTenantConstant.TENANT)
    public R put(@PathVariable("id") String id, @RequestBody SysTenantUpdateVO sysTenantUpdateVO) {
        sysTenantService.update(id, sysTenantUpdateVO);
        return R.empty();
    }

    @DeleteMapping("/{id}")
    @Api(value = "集团管理-删除", accessCodes = AccessTenantConstant.TENANT)
    public R delete(@PathVariable("id") String id) {
        sysTenantService.delete(id);
        return R.empty();
    }

    @DeleteMapping
    @Api(value = "集团管理-批量删除", accessCodes = AccessTenantConstant.TENANT)
    public R batchDelete(@RequestBody DataBody<List<String>> dataBody) {
        sysTenantService.batchDelete(dataBody);
        return R.empty();
    }

}
