package com.jerry.up.lala.boot.vo;

import lombok.Data;

/**
 * <p>Description: 字典项
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
public class SysDictItemVO {

    /**
     * 字典项ID
     */
    private Long id;

    /**
     * 展示名
     */
    private String label;

    /**
     * 值
     */
    private String value;

}
