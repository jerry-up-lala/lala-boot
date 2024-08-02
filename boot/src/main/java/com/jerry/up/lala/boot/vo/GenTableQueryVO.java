package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.dto.GenTableQueryDTO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import com.jerry.up.lala.framework.common.model.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * <p>Description: 代码生成#表信息#查询条件
 *
 * @author FMJ
 * @date 2024/2/2 13:44
 */
@Data
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@DataBean(targetTypes = GenTableQueryDTO.class)
public class GenTableQueryVO extends PageQuery {

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
     * 创建日期
     */
    private List<String> tableCreateTimeRang;

    /**
     * 修改日期
     */
    private List<String> tableUpdateTimeRang;

}
