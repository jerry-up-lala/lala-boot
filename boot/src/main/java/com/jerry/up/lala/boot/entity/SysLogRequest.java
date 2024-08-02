package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.framework.boot.entity.Entity;
import com.jerry.up.lala.framework.common.annotation.DataFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 请求日志表
 *
 * @author FMJ
 * @TableName sys_log_request
 */
@TableName(value = "sys_log_request")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLogRequest extends Entity {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 集团ID
     */
    private String tenantId;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 账号ID
     */
    private String userId;

    /**
     * 用户信息
     */
    private String userInfo;

    /**
     * 模块
     */
    private String apiName;

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
     * 是否成功
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
     * 响应耗时(毫秒)
     */
    private Long responseTime;

    /**
     * 响应耗时格式化
     */
    private String responseTimeFormat;

    /**
     * 操作时间
     */
    private Date requestTime;

    /**
     * 请求平台
     */
    private String userAgent;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 请求协议
     */
    private String scheme;

    /**
     * servlet 路径
     */
    @DataFormat(maxLength = 255)
    private String servletPath;

    /**
     * 请求方式
     */
    private String servletMethod;

    /**
     * 请求路径
     */
    @DataFormat(maxLength = 255)
    private String requestUrl;

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
     * 服务名
     */
    private String serverName;

    /**
     * 服务端口
     */
    private Integer serverPort;

    /**
     * 客户端host
     */
    private String remoteHost;

    /**
     * 客户端端口
     */
    private Integer remotePort;

    /**
     * 服务端地址
     */
    private String localAddr;

    /**
     * 服务端名称
     */
    private String localName;

    /**
     * 服务端端口
     */
    private Integer localPort;

    /**
     * 请求信息
     */
    @DataFormat(maxLength = 255)
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