package com.jerry.up.lala.boot.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.jerry.up.lala.boot.dto.SysLogRequestApiNameDTO;
import com.jerry.up.lala.boot.dto.SysLogRequestServletMethodDTO;
import com.jerry.up.lala.boot.dto.SysLogRequestStatisticDTO;
import com.jerry.up.lala.boot.dto.SysLogRequestSumDTO;
import com.jerry.up.lala.boot.entity.SysLogRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author jerry
 * @description 针对表【sys_log_request(请求日志表)】的数据库操作Mapper
 * @createDate 2023-12-22 14:58:06
 * @Entity com.jerry.up.lala.boot.entity.SysLogRequest
 */
public interface SysLogRequestMapper extends MPJBaseMapper<SysLogRequest> {

    SysLogRequestStatisticDTO statistic(@Param("yesterday") String yesterday, @Param("today") String today);

    List<SysLogRequestSumDTO> sum(@Param("requestDateList") Collection<String> requestDateList);

    List<SysLogRequestServletMethodDTO> servletMethod();

    List<SysLogRequestApiNameDTO> apiName();
}




