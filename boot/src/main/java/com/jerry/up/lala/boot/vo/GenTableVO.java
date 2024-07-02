package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>Description: 代码生成#表信息
 *
 * @author FMJ
 * @date 2024/2/2 13:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenTableVO extends GenTableSaveVO {

    /**
     * 表ID
     *
     * @see com.jerry.up.lala.boot.entity.SysGenTable#getId()
     */
    private Long id;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date tableCreateTime;

    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date tableUpdateTime;

    /**
     * 排序规则
     */
    private String tableCollation;

}
