package com.jerry.up.lala.boot.vo;

import lombok.Data;

/**
 * <p>Description: 代码生成#表信息
 *
 * @author FMJ
 * @date 2024/2/2 13:44
 */
@Data
public class GenTableSaveVO {

    /**
     * 库名
     */
    private String tableSchema;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 类名
     */
    private String className;

    /**
     * 功能名
     */
    private String functionName;

    /**
     * 作者
     */
    private String author;

}
