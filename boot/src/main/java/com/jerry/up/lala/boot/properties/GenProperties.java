package com.jerry.up.lala.boot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 代码生成配置
 *
 * @author FMJ
 * @date 2024/2/12 13:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "gen")
public class GenProperties {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 包名
     */
    private String packageName;

}
