package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.boot.bo.CrudExcelBO;
import com.jerry.up.lala.boot.vo.CrudInfoVO;
import com.jerry.up.lala.boot.vo.CrudSaveVO;
import com.jerry.up.lala.framework.core.common.Entity;
import com.jerry.up.lala.framework.core.data.DataBean;
import com.jerry.up.lala.framework.core.data.DataFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.Date;

/**
 * crud表
 *
 * @author FMJ
 */
@TableName(value = "crud")
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@DataBean(targetTypes = {CrudInfoVO.class, CrudExcelBO.class})
public class Crud extends Entity {
    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 输入框
     */
    private String input;

    /**
     * 数字输入框
     */
    private BigDecimal inputNumber;

    /**
     * 标签输入框
     */
    @DataFormat(sourceFieldName = CrudSaveVO.Fields.inputTagList, split = ",")
    private String inputTags;

    /**
     * 自动补全
     */
    private String autoComplete;

    /**
     * 提及
     */
    private String mention;

    /**
     * 文本域
     */
    private String textArea;

    /**
     * 评分
     */
    private BigDecimal rate;

    /**
     * 滑动输入条
     */
    private Integer slider;

    /**
     * 开关(STATE),单选
     */
    private String switchInfo;

    /**
     * 单选框(CRUD_LIST),单选
     */
    private String radio;

    /**
     * 复选框(CRUD_LIST),多选
     */
    @DataFormat(sourceFieldName = CrudSaveVO.Fields.checkboxList, split = ",")
    private String checkboxes;

    /**
     * 选择器(CRUD_LIST),单选
     */
    private String selectInfo;

    /**
     * 级联选择(CRUD_TREE),单选
     */
    private String cascader;

    /**
     * 树选择(CRUD_TREE),单选
     */
    private String treeSelect;

    /**
     * 数据穿梭框(CRUD_LIST),多选
     */
    @DataFormat(sourceFieldName = CrudSaveVO.Fields.transferList, split = ",")
    private String transfers;

    /**
     * 日期时间选择器
     */
    private Date dateTimePicker;

    /**
     * 上传
     */
    private String upload;

}