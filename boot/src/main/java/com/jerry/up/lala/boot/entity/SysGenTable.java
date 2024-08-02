package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.boot.dto.GenTableDTO;
import com.jerry.up.lala.framework.boot.entity.Entity;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 数据库表
 *
 * @author FMJ
 * @TableName sys_gen_table
 */
@TableName(value = "sys_gen_table")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@DataBean(targetTypes = GenTableDTO.class)
public class SysGenTable extends Entity {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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