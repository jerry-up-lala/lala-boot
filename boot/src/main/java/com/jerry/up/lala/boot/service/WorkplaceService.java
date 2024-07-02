package com.jerry.up.lala.boot.service;

import com.jerry.up.lala.boot.vo.*;

import java.util.List;

/**
 * <p>Description: 工作空间
 *
 * @author FMJ
 * @date 2024/1/19 13:39
 */
public interface WorkplaceService {
    WorkplaceStatisticVO statistic();

    AntVDualAxesVO logSum();

    List<AntVPieVO> logServletMethod();

    List<SysLogRequestApiNameVO> logApiName();

    List<NoticeUserVO> notice();
}
