package com.jerry.up.lala.boot.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 请求日志汇总DTO
 *
 * @author FMJ
 * @date 2023/12/22 13:41
 */
@Data
@Accessors(chain = true)
public class SysLogRequestSumDTO {

    /**
     * 日期
     */
    private String date;

    /**
     * 数量
     */
    private Long count;

    /**
     * 响应成功
     */
    private Long responseSuccessCount;

    /**
     * 响应失败
     */
    private Long responseErrorCount;

}
