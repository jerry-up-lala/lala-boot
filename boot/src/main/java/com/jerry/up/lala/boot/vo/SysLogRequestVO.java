package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jerry.up.lala.framework.core.data.DataFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>Description: 访问日志VO
 *
 * @author FMJ
 * @date 2023/12/22 13:08
 */
@Data
public class SysLogRequestVO {

    /**
     * 主键ID
     */
    private Long id;

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
     * 租户Id
     */
    private String tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 用户信息
     */
    private String userInfo;

    /**
     * 请求方式
     */
    private String servletMethod;

    /**
     * 请求地址
     */
    private String servletPath;

    /**
     * 响应耗时格式化
     */
    private String responseTimeFormat;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date requestTime;

    /**
     * 类方法
     */
    private String classMethod;

    /**
     * 请求参数
     */
    @DataFormat(maxLength = 50000)
    private String classParams;

    /**
     * 请求平台
     */
    private String userAgent;

    /**
     * 请求参数
     */
    @DataFormat(maxLength = 50000)
    private String requestParams;

    /**
     * 请求body
     */
    @DataFormat(maxLength = 50000)
    private String requestBody;

    /**
     * 请求路径
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
