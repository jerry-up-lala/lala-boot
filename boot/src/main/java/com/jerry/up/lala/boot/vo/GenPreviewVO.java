package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 代码预览
 *
 * @author FMJ
 * @date 2024/02/20 09:45
 */
@Data
@Accessors(chain = true)
public class GenPreviewVO {

    /**
     * 代码树
     */
    private List<GenPreviewTreeVO> tree;

    /**
     * 代码tab
     */
    private List<GenPreviewTabVO> tab;

}
