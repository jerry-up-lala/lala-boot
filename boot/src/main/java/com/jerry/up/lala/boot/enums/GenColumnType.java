package com.jerry.up.lala.boot.enums;

import cn.hutool.core.collection.ListUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * <p>Description: 字段类型枚举
 *
 * @author FMJ
 * @date 2024/2/2 17:35
 */
@Getter
@AllArgsConstructor
public enum GenColumnType {

    STRING("String", "VARCHAR", ListUtil.of("char", "varchar", "tinytext", "text", "mediumtext", "longtext", "VARCHAR2", "NVARCHAR", "NVARCHAR2", "CLOB", "json")),
    INTEGER("Integer", "INTEGER", ListUtil.of("tinyint", "smallint", "mediumint", "int", "integer", "NUMBER", "BINARY_INTEGER", "int4", "int2")),
    BIG_DECIMAL("BigDecimal", "DECIMAL", ListUtil.of("decimal", "numeric")),
    DATE("Date", "TIMESTAMP", ListUtil.of("date", "datetime", "timestamp")),
    LONG("Long", "BIGINT", ListUtil.of("bigint", "int8")),
    BOOLEAN("Boolean", "BOOLEAN", ListUtil.of("bit")),
    DOUBLE("Double", "DOUBLE", ListUtil.of("double", "BINARY_DOUBLE")),
    FLOAT("Float", "FLOAT", ListUtil.of("float", "BINARY_FLOAT"));

    private final String fieldType;

    private final String jdbcType;

    private final List<String> columnTypeList;

    public static GenColumnType type(String dbType) {
        for (GenColumnType genColumnType : GenColumnType.values()) {
            if (genColumnType.columnTypeList.contains(dbType)) {
                return genColumnType;
            }
        }
        return GenColumnType.STRING;
    }

}
