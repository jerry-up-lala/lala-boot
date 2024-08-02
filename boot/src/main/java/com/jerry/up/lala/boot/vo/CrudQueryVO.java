package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.dto.CrudQueryDTO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import com.jerry.up.lala.framework.common.model.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Description: crud 查询
 *
 * @author FMJ
 * @date 2023/10/25 11:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@DataBean(targetTypes = CrudQueryDTO.class)
public class CrudQueryVO extends PageQuery {

    private String input;

    private BigDecimal inputNumberStart;

    private BigDecimal inputNumberEnd;

    private List<String> inputTagList;

    private String autoComplete;

    private String mention;

    private String textArea;

    private BigDecimal rateStart;

    private BigDecimal rateEnd;

    private Integer sliderStart;

    private Integer sliderEnd;

    private Boolean switchInfo;

    private List<String> radioList;

    private List<String> checkboxList;

    private List<String> selectInfoList;

    private List<String> cascaderList;

    private List<String> treeSelectList;

    private List<String> transferList;

    private List<String> dateTimePickerRang;

}
