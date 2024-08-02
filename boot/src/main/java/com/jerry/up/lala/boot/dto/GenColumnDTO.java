package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.bo.GenTemplateColumnBO;
import com.jerry.up.lala.boot.entity.SysGenColumn;
import com.jerry.up.lala.boot.enums.GenColumnType;
import com.jerry.up.lala.boot.vo.GenColumnVO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 代码生成#列信息
 *
 * @author FMJ
 * @date 2024/2/2 13:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DataBean(targetTypes = {GenColumnVO.class, GenTemplateColumnBO.class})
public class GenColumnDTO extends SysGenColumn {

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
     * 列排序位置
     */
    private Integer columnOrdinalPosition;

    /**
     * 列jdbc类型
     * @see GenColumnType#getJdbcType()
     */
    private String columnJdbcType;

}
