package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 代码预览树
 *
 * @author FMJ
 * @date 2024/02/20 09:45
 */
@Data
@Accessors(chain = true)
public class GenPreviewTreeVO {

    /**
     * 完整路径
     */
    private String key;

    /**
     * 标题
     */
    private String title;

    /**
     * 子节点
     */
    private List<GenPreviewTreeVO> children;
}
