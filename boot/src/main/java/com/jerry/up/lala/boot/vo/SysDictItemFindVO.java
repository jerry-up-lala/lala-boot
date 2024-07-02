package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 字典项 信息
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
@Accessors(chain = true)
public class SysDictItemFindVO {

    /**
     * 展示名
     */
    private String label;

    /**
     * 值
     */
    private String value;

    /**
     * 名称列表
     */
    private String labels;

    /**
     * 值列表
     */
    private String values;

}
