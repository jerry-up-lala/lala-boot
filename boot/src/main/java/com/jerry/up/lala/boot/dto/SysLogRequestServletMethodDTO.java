package com.jerry.up.lala.boot.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 请求日志方法DTO
 *
 * @author FMJ
 * @date 2023/12/22 13:41
 */
@Data
@Accessors(chain = true)
public class SysLogRequestServletMethodDTO {

    /**
     * 请求方法
     */
    private String servletMethod;

    /**
     * 数量
     */
    private Long count;

}
