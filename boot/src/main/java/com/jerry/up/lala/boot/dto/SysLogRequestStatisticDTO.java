package com.jerry.up.lala.boot.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>Description: 请求日志统计DTO
 *
 * @author FMJ
 * @date 2023/12/22 13:41
 */
@Data
@Accessors(chain = true)
public class SysLogRequestStatisticDTO {

    /**
     * 总请求次数
     */
    private Long request;

    /**
     * 昨日请求次数
     */
    private Long yesterday;

    /**
     * 今日请求次数
     */
    private Long today;

    /**
     * 较昨日新增
     */
    private BigDecimal requestQoq;

}
