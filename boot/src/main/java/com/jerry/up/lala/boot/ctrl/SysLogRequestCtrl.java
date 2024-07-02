package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.access.AccessLogRequestConstant;
import com.jerry.up.lala.boot.service.SysLogRequestService;
import com.jerry.up.lala.boot.vo.SysLogRequestQueryVO;
import com.jerry.up.lala.boot.vo.SysLogRequestVO;
import com.jerry.up.lala.framework.core.common.Api;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 访问记录
 *
 * @author FMJ
 * @date 2023/12/22 11:31
 */
@RestController
@RequestMapping("/sys/log-request")
public class SysLogRequestCtrl {

    @Autowired
    private SysLogRequestService sysLogRequestService;

    @GetMapping("/page")
    @Api(value = "请求日志-查询", accessCodes = {
            AccessLogRequestConstant.LOG_REQUEST, AccessLogRequestConstant.LOG_REQUEST_QUERY
    }, log = false)
    public R<PageR<SysLogRequestVO>> page(SysLogRequestQueryVO sysLogRequestQueryVO) {
        PageR<SysLogRequestVO> pageR = sysLogRequestService.pageQuery(sysLogRequestQueryVO);
        return R.succeed(pageR);
    }
}
