package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;

import java.util.Date;

/**
 * <p>Description: information_schema.TABLES
 *
 * @author FMJ
 * @date 2024/2/27 17:44
 */
@Data
@DataBean(targetTypes = GenTableDTO.class)
public class InformationSchemaTableDTO {

    /**
     * 库名
     */
    private String tableSchema;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 备注
     */
    private String tableComment;

    /**
     * 引擎
     */
    private String tableEngine;

    /**
     * 创建日期
     */
    private Date tableCreateTime;

    /**
     * 修改日期
     */
    private Date tableUpdateTime;

    /**
     * 排序规则
     */
    private String tableCollation;
}
