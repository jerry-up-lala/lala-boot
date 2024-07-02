package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.vo.GenTableQueryVO;
import com.jerry.up.lala.framework.core.data.DataFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: 代码生成#表信息#查询条件
 *
 * @author FMJ
 * @date 2024/2/2 13:44
 */
@Data
public class GenTableQueryDTO {

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
     * 排序规则
     */
    private String tableCollation;

    @DataFormat(sourceFieldName = GenTableQueryVO.Fields.tableCreateTimeRang, listIndex = 0, dateType = 2)
    private Date tableCreateTimeStart;

    @DataFormat(sourceFieldName = GenTableQueryVO.Fields.tableCreateTimeRang, listIndex = 1, dateType = 2)
    private Date tableCreateTimeEnd;

    @DataFormat(sourceFieldName = GenTableQueryVO.Fields.tableUpdateTimeRang, listIndex = 0, dateType = 2)
    private Date tableUpdateTimeStart;

    @DataFormat(sourceFieldName = GenTableQueryVO.Fields.tableUpdateTimeRang, listIndex = 1, dateType = 2)
    private Date tableUpdateTimeEnd;

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

    /**
     * 是否查询表名
     */
    private Boolean queryTableName;

    /**
     * 查询表名列表
     */
    private List<String> queryTableNameList;

}
