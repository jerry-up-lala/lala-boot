package com.jerry.up.lala.boot.service;

import com.github.yulichang.base.MPJBaseService;
import com.jerry.up.lala.boot.dto.SysLogRequestApiNameDTO;
import com.jerry.up.lala.boot.dto.SysLogRequestServletMethodDTO;
import com.jerry.up.lala.boot.dto.SysLogRequestStatisticDTO;
import com.jerry.up.lala.boot.dto.SysLogRequestSumDTO;
import com.jerry.up.lala.boot.entity.SysLogRequest;
import com.jerry.up.lala.boot.vo.SysLogRequestQueryVO;
import com.jerry.up.lala.boot.vo.SysLogRequestVO;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.api.ApiLog;

import java.util.List;

/**
 * @author jerry
 * @description 针对表【sys_log_request(请求日志表)】的数据库操作Service
 * @createDate 2023-12-22 11:25:01
 */
public interface SysLogRequestService extends MPJBaseService<SysLogRequest> {

    void save(ApiLog apiLog);

    PageR<SysLogRequestVO> pageQuery(SysLogRequestQueryVO sysLogRequestQueryVO);

    SysLogRequestStatisticDTO statistic();

    List<SysLogRequestSumDTO> sum();

    List<SysLogRequestServletMethodDTO> servletMethod();

    List<SysLogRequestApiNameDTO> apiName();
}
