package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 柱线混合图表-显示多折线#数据
 *
 * @author FMJ
 * @date 2024/1/19 13:31
 */
@Data
@Accessors(chain = true)
public class AntVDualAxesDataVO {

    /**
     * x轴
     */
    private String x;

    /**
     * y轴值
     */
    private Long y;

    /**
     * 类型
     */
    private String name;
}
