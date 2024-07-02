package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: <a href="https://g2plot.antv.antgroup.com/zh/examples/pie/donut/#statistics">环图</a>
 *
 * @author FMJ
 * @date 2024/1/19 13:31
 */
@Data
@Accessors(chain = true)
public class AntVPieVO {

    /**
     * 类型
     */
    private String type;

    /**
     * 值
     */
    private Long value;
}
