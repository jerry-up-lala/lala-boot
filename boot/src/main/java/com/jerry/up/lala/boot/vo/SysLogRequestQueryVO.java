package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.dto.SysLogRequestQueryDTO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import com.jerry.up.lala.framework.common.model.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * <p>Description: 访问日志VO
 *
 * @author FMJ
 * @date 2023/12/22 13:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@DataBean(targetTypes = SysLogRequestQueryDTO.class)
public class SysLogRequestQueryVO extends PageQuery {

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
     * 操作时间
     */
    private List<String> requestTimeRange;

    /**
     * 响应耗时(毫秒) 开始
     */
    private List<Long> responseTimeRange;

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
