package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jerry.up.lala.boot.dto.*;
import com.jerry.up.lala.boot.service.NoticeUserService;
import com.jerry.up.lala.boot.service.SysLogRequestService;
import com.jerry.up.lala.boot.service.SysMenuService;
import com.jerry.up.lala.boot.service.WorkplaceService;
import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.core.api.ApiProperties;
import com.jerry.up.lala.framework.core.common.Errors;
import com.jerry.up.lala.framework.core.data.DataUtil;
import com.jerry.up.lala.framework.core.exception.ServiceException;
import com.jerry.up.lala.framework.core.satoken.SaTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: 工作空间实现
 *
 * @author FMJ
 * @date 2024/1/19 13:44
 */
@Service
public class WorkplaceServiceImpl implements WorkplaceService {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysLogRequestService sysLogRequestService;

    @Autowired
    private NoticeUserService noticeUserService;

    @Autowired
    private ApiProperties apiProperties;

    @Override
    public WorkplaceStatisticVO statistic() {
        try {
            Long menu = sysMenuService.count(Wrappers.emptyWrapper());
            SysLogRequestStatisticDTO statisticDTO = sysLogRequestService.statistic();
            return new WorkplaceStatisticVO().setMenu(menu)
                    .setApiCount(apiProperties.getCount())
                    .setApiDoc(apiProperties.getDoc())
                    .setRequest(statisticDTO.getRequest())
                    .setRequestQoq(statisticDTO.getRequestQoq());
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public AntVDualAxesVO logSum() {
        try {
            List<SysLogRequestSumDTO> sumList = sysLogRequestService.sum();
            List<AntVDualAxesDataVO> column = new ArrayList<>();
            List<AntVDualAxesDataVO> data = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(sumList)) {
                for (SysLogRequestSumDTO sumDTO : sumList) {
                    String date = sumDTO.getDate();
                    column.add(new AntVDualAxesDataVO().setX(date).setY(sumDTO.getCount()));
                    data.add(new AntVDualAxesDataVO().setX(date).setY(sumDTO.getResponseSuccessCount())
                            .setName("1"));
                    data.add(new AntVDualAxesDataVO().setX(date).setY(sumDTO.getResponseErrorCount())
                            .setName("2"));
                }
            }
            return new AntVDualAxesVO().setColumn(column).setData(data);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public List<AntVPieVO> logServletMethod() {
        try {
            List<SysLogRequestServletMethodDTO> list = sysLogRequestService.servletMethod();
            return list.stream().map(item -> new AntVPieVO().setType(item.getServletMethod()).setValue(item.getCount())).collect(Collectors.toList());
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public List<SysLogRequestApiNameVO> logApiName() {
        try {
            List<SysLogRequestApiNameDTO> sysLogRequestApiNameDTO = sysLogRequestService.apiName();
            return DataUtil.toBeanList(sysLogRequestApiNameDTO, SysLogRequestApiNameVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public List<NoticeUserVO> notice() {
        NoticeUserQueryDTO queryDTO = new NoticeUserQueryDTO().setUserId(SaTokenUtil.currentUser().getUserId()).setLimit(5L);
        return noticeUserService.listQuery(queryDTO);
    }
}
