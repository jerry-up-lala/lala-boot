package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.entity.SysGenTable;
import com.jerry.up.lala.boot.vo.GenTableVO;
import com.jerry.up.lala.framework.core.data.DataBean;
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
@DataBean(targetTypes = GenTableVO.class)
public class GenTableDTO extends SysGenTable {

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
