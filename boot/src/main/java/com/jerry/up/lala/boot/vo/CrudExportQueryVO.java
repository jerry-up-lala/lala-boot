package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.dto.CrudQueryDTO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>Description: 导出查询
 *
 * @author FMJ
 * @date 2023/11/15 23:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DataBean(targetTypes = CrudQueryDTO.class)
public class CrudExportQueryVO extends CrudQueryVO {

    private List<String> ids;

    /**
     * 是否导出当前页
     */
    private Boolean currentPage;
}
