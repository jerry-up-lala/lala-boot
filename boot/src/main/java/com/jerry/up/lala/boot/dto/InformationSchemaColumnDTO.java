package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.enums.GenColumnType;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;

/**
 * <p>Description: information_schema.COLUMNS
 *
 * @author FMJ
 * @date 2024/2/27 17:44
 */
@Data
@DataBean(targetTypes = GenColumnDTO.class)
public class InformationSchemaColumnDTO {

    /**
     * 库名
     */
    private String tableSchema;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列索引类型 主键-PRI,唯一索引-UNI,一般索引-MUL
     */
    private String columnKey;

    /**
     * 额外信息 auto_increment-自动递增
     */
    private String columnExtra;

    /**
     * 列备注
     */
    private String columnComment;

    /**
     * 列数据类型
     */
    private String columnDataType;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * 字段排序位置
     */
    private Integer columnOrdinalPosition;

    /**
     * 列jdbc类型
     *
     * @see GenColumnType#getJdbcType()
     */
    private String columnJdbcType;


}
