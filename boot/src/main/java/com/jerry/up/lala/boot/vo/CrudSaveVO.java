package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jerry.up.lala.boot.entity.Crud;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: crud 保存 VO
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@FieldNameConstants
@DataBean(targetTypes = Crud.class)
public class CrudSaveVO {

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
    private List<String> inputTagList;

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
    private List<String> checkboxList;

    /**
     * 选择器(CRUD_LIST),单选
     */
    private Integer selectInfo;

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
    private List<String> transferList;

    /**
     * 日期时间选择器
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dateTimePicker;

    /**
     * 上传
     */
    private String upload;
}
