package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jerry.up.lala.boot.entity.Crud;
import com.jerry.up.lala.framework.core.data.DataFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: crud 信息 VO
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@Accessors(chain = true)
public class CrudInfoVO {

    /**
     * 主键ID
     */
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
    @DataFormat(sourceFieldName = Crud.Fields.inputTags, split = ",")
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
     * 开关
     */
    private String switchInfo;

    /**
     * 单选框
     */
    private String radio;

    /**
     * 复选框
     */
    @DataFormat(sourceFieldName = Crud.Fields.checkboxes, split = ",")
    private List<String> checkboxList;

    /**
     * 选择器
     */
    private String selectInfo;

    /**
     * 级联选择
     */
    private String cascader;

    /**
     * 树选择
     */
    private String treeSelect;

    /**
     * 数据穿梭框
     */
    @DataFormat(sourceFieldName = Crud.Fields.transfers, split = ",")
    private List<String> transferList;

    /**
     * 日期时间选择器
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date dateTimePicker;

    /**
     * 上传
     */
    private String upload;

}
