package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.jerry.up.lala.framework.core.common.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * 字典项
 *
 * @author FMJ
 * @TableName sys_dict_item
 */
@TableName(value = "sys_dict_item")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class SysDictItem extends Entity {
    /**
     * 字典ID
     */
    @TableId(type = IdType.AUTO)
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
    @TableField(insertStrategy = FieldStrategy.ALWAYS, updateStrategy = FieldStrategy.ALWAYS, whereStrategy = FieldStrategy.ALWAYS)
    private String value;

    /**
     * 排序号(值越高，越靠后)
     */
    private Integer sortOrder;

}