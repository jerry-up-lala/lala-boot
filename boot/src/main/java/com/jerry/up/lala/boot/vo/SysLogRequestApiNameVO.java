package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>Description: 请求日志接口名称VO
 *
 * @author FMJ
 * @date 2023/12/22 13:41
 */
@Data
@Accessors(chain = true)
public class SysLogRequestApiNameVO {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date maxRequestTime;

}
