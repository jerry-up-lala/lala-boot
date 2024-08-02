package com.jerry.up.lala.boot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 上传配置
 *
 * @author FMJ
 * @date 2023/9/6 13:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {

    private Integer max;

    private Integer partition;

}
