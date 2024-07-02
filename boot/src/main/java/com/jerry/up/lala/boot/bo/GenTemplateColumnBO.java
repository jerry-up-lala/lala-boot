package com.jerry.up.lala.boot.bo;

import com.jerry.up.lala.boot.enums.GenColumnType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 模板列BO
 *
 * @author FMJ
 * @date 2024/2/14 21:11
 */
@Data
@Accessors(chain = true)
public class GenTemplateColumnBO {

    /**
     * 列索引类型 主键-PRI,唯一索引-UNI,一般索引-MUL
     */
    private String columnKey;

    /**
     * 额外信息 auto_increment-自动递增
     */
    private String columnExtra;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列jdbc类型
     * @see GenColumnType#getJdbcType()
     */
    private String columnJdbcType;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段名首字母大写
     */
    private String upperFirstFieldName;

    /**
     * 字段是否变更
     */
    private Boolean fieldChange;

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

}
