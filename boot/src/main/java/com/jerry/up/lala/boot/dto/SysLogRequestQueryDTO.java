package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.vo.SysLogRequestQueryVO;
import com.jerry.up.lala.framework.common.annotation.DataFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: 系统请求记录查询条件
 *
 * @author FMJ
 * @date 2023/12/22 13:41
 */
@Data
public class SysLogRequestQueryDTO {

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 响应状态，true-成功，false-失败
     */
    private Boolean responseSuccess;

    /**
     * 响应异常码
     */
    private String responseErrorCode;

    /**
     * 响应异常信息
     */
    private String responseErrorMsg;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 集团ID
     */
    private List<String> tenantIds;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 账号ID
     */
    private String userId;

    /**
     * 请求方式
     */
    private List<String> servletMethods;

    /**
     * 请求地址
     */
    private String servletPath;

    /**
     * 操作时间 开始
     */
    @DataFormat(sourceFieldName = SysLogRequestQueryVO.Fields.requestTimeRange, listIndex = 0, dateType = 2)
    private Date requestTimeStart;

    /**
     * 操作时间 结束
     */
    @DataFormat(sourceFieldName = SysLogRequestQueryVO.Fields.requestTimeRange, listIndex = 1, dateType = 2)
    private Date requestTimeEnd;

    /**
     * 响应耗时(毫秒) 开始
     */
    @DataFormat(sourceFieldName = SysLogRequestQueryVO.Fields.responseTimeRange, listIndex = 0)
    private Long responseTimeStart;

    /**
     * 响应耗时(毫秒) 开始
     */
    @DataFormat(sourceFieldName = SysLogRequestQueryVO.Fields.responseTimeRange, listIndex = 1)
    private Long responseTimeEnd;

    /**
     * 类方法
     */
    private String classMethod;

    /**
     * 请求参数
     */
    private String classParams;

    /**
     * 请求平台
     */
    private String userAgent;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求body
     */
    private String requestBody;

    /**
     * 请求URL
     */
    private String requestUrlInfo;

    /**
     * 客户端信息
     */
    private String clientInfo;

    /**
     * 服务端信息
     */
    private String serverInfo;

}
