package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.vo.CrudQueryVO;
import com.jerry.up.lala.framework.common.annotation.DataFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: crud 查询DTO
 *
 * @author FMJ
 * @date 2023/10/25 13:54
 */
@Data
@Accessors(chain = true)
public class CrudQueryDTO {

    private List<String> ids;

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

    private String switchInfo;

    private List<String> radioList;

    private List<String> checkboxList;

    private List<String> selectInfoList;

    private List<String> cascaderList;

    private List<String> treeSelectList;

    private List<String> transferList;

    @DataFormat(sourceFieldName = CrudQueryVO.Fields.dateTimePickerRang, listIndex = 0, dateType = 2)
    private Date dateTimePickerStart;

    @DataFormat(sourceFieldName = CrudQueryVO.Fields.dateTimePickerRang, listIndex = 1, dateType = 2)
    private Date dateTimePickerEnd;
}
