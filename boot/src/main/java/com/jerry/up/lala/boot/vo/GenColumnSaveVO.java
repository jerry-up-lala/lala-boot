package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.enums.GenColumnType;
import com.jerry.up.lala.boot.enums.SysDictKey;
import lombok.Data;

/**
 * <p>Description: 代码生成#表信息#字段信息
 *
 * @author FMJ
 * @date 2024/2/2 13:44
 */
@Data
public class GenColumnSaveVO {

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

}
