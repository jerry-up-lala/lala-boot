package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.framework.boot.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 集团表
 *
 * @author FMJ
 */
@TableName(value = "sys_tenant")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysTenant extends Entity {
    /**
     * 集团ID
     */
    @TableId
    private String id;

    /**
     * 集团名称(全局唯一)
     */
    private String tenantName;

    /**
     * 状态[true-启用]
     */
    private Boolean state;

}