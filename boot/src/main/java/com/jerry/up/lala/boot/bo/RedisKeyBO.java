package com.jerry.up.lala.boot.bo;

import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 缓存key信息
 *
 * @author FMJ
 * @date 2023/11/9 10:30
 */
@Data
@Accessors(chain = true)
@DataBean(targetTypes = RedisInfoBO.class)
public class RedisKeyBO {

    private String key;

    /**
     * @see org.springframework.data.redis.connection.DataType; 数据类型
     */
    private String dataType;

    private Long expire;

    private String expireFormat;

}
