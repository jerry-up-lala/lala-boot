package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 代码生成#表信息#字段信息
 *
 * @author FMJ
 * @date 2024/2/2 13:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenColumnVO extends GenColumnSaveVO {

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
     * 列类型
     */
    private String columnType;

}
