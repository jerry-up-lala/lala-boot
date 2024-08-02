package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.vo.SysLogRequestApiNameVO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>Description: 请求日志接口名称DTO
 *
 * @author FMJ
 * @date 2023/12/22 13:41
 */
@Data
@Accessors(chain = true)
@DataBean(targetTypes = SysLogRequestApiNameVO.class)
public class SysLogRequestApiNameDTO {

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 数量
     */
    private Long count;

    /**
     * 最新请求时间
     */
    private Date maxRequestTime;

}
