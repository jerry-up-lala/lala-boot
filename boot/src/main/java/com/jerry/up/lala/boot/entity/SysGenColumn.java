package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.boot.enums.GenColumnType;
import com.jerry.up.lala.boot.enums.SysDictKey;
import com.jerry.up.lala.framework.boot.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 数据库字段
 *
 * @author FMJ
 * @TableName sys_gen_column
 */
@TableName(value = "sys_gen_column")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysGenColumn extends Entity {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 表ID
     */
    private Long tableId;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段类型
     *
     * @see GenColumnType#getFieldType()
     */
    private String fieldType;

    /**
     * 字段备注
     */
    private String fieldComment;

    /**
     * 字典类型
     * @see SysDictKey#getValue()
     */
    private String fieldDictKey;

    /**
     * 字段排序位置
     */
    private Integer fieldOrdinalPosition;

}