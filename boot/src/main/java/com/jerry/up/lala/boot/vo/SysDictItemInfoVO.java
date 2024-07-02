package com.jerry.up.lala.boot.vo;

import lombok.Data;

/**
 * <p>Description: 字典项
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
public class SysDictItemInfoVO {

    /**
     * 字典项ID
     */
    private Long id;

    /**
     * 字典类型ID(sys_dict.id)
     */
    private Long dictId;

    /**
     * 字典父ID
     */
    private Long parentId;

    /**
     * 展示名
     */
    private String label;

    /**
     * 值
     */
    private String value;

    /**
     * 排序号(值越高，越靠后)
     */
    private Integer sortOrder;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典标识
     */
    private String dictKey;
}
