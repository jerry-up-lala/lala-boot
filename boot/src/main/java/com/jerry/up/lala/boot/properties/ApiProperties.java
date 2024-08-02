package com.jerry.up.lala.boot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Description: api配置
 *
 * @author FMJ
 * @date 2023/9/6 13:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    /**
     * 接口文档地址
     */
    private String doc;

    /**
     * 接口数量
     */
    private Integer count;

}
