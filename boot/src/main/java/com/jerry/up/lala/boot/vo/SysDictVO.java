package com.jerry.up.lala.boot.vo;

import lombok.Data;

/**
 * <p>Description: 字典
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
public class SysDictVO {

    /**
     * 字典ID
     */
    private Long id;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典标识
     */
    private String dictKey;
}
