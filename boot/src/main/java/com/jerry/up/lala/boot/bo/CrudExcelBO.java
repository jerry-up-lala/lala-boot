package com.jerry.up.lala.boot.bo;

import com.jerry.up.lala.boot.entity.Crud;
import com.jerry.up.lala.framework.boot.excel.ExcelFormat;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Description: crud Excel BO
 *
 * @author FMJ
 * @date 2023/11/14 15:11
 */
@Data
@DataBean(targetTypes = Crud.class)
public class CrudExcelBO {

    /**
     * 输入框
     */
    @ExcelFormat(headerAlias = "输入框", require = true, maxLength = 15)
    private String input;

    /**
     * 数字输入框
     */
    @ExcelFormat(headerAlias = "数字输入框", require = true, maxValue = 1000000000000d, index = 1)
    private BigDecimal inputNumber;

    /**
     * 标签输入框
     */
    @ExcelFormat(headerAlias = "标签输入框", index = 2)
    private String inputTags;

    /**
     * 自动补全
     */
    @ExcelFormat(headerAlias = "自动补全", index = 3)
    private String autoComplete;

    /**
     * 提及
     */
    @ExcelFormat(headerAlias = "提及", index = 4)
    private String mention;

    /**
     * 文本域
     */
    @ExcelFormat(headerAlias = "文本域", index = 5)
    private String textArea;

    /**
     * 评分
     */
    @ExcelFormat(headerAlias = "评分", index = 6)
    private BigDecimal rate;

    /**
     * 滑动输入条
     */
    @ExcelFormat(headerAlias = "滑动输入条", index = 7)
    private Integer slider;

    /**
     * 开关
     */
    @ExcelFormat(headerAlias = "开关", require = true, index = 8)
    private String switchInfo;

    /**
     * 单选框
     */
    @ExcelFormat(headerAlias = "单选框", index = 9)
    private String radio;

    /**
     * 复选框
     */
    @ExcelFormat(headerAlias = "复选框", index = 10)
    private String checkboxes;

    /**
     * 选择器
     */
    @ExcelFormat(headerAlias = "选择器", index = 11)
    private String selectInfo;

    /**
     * 级联选择
     */
    @ExcelFormat(headerAlias = "级联选择", index = 12)
    private String cascader;

    /**
     * 树选择
     */
    @ExcelFormat(headerAlias = "树选择", index = 13)
    private String treeSelect;

    /**
     * 数据穿梭框
     */
    @ExcelFormat(headerAlias = "数据穿梭框", index = 14)
    private String transfers;

    /**
     * 日期时间选择器
     */
    @ExcelFormat(headerAlias = "日期时间选择器", index = 15)
    private Date dateTimePicker;

}
