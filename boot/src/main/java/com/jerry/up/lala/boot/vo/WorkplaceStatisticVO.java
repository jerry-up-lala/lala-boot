package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>Description: 工作台#统计数量
 *
 * @author FMJ
 * @date 2024/1/19 13:31
 */
@Data
@Accessors(chain = true)
public class WorkplaceStatisticVO {

    /**
     * 总菜单数量
     */
    private Long menu;

    /**
     * 总接口数量
     */
    private Integer apiCount;

    /**
     * 接口地址
     */
    private String apiDoc;

    /**
     * 总请求次数
     */
    private Long request;

    /**
     * 环比
     */
    private BigDecimal requestQoq;
}
