package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 柱线混合图表-显示多折线
 * <a href="https://g2plot.antv.antgroup.com/examples/dual-axes/column-line/#column-multi-line">柱线混合图表-显示多折线</a>
 *
 * @author FMJ
 * @date 2024/1/19 13:31
 */
@Data
@Accessors(chain = true)
public class AntVDualAxesVO {

    /**
     * 柱状图数据
     */
    private List<AntVDualAxesDataVO> column;

    /**
     * 折线图数据
     */
    private List<AntVDualAxesDataVO> data;

}
