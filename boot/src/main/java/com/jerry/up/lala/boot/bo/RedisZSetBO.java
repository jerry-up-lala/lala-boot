package com.jerry.up.lala.boot.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: redis zset 对象
 *
 * @author FMJ
 * @date 2023/11/9 13:20
 */
@Data
@Accessors(chain = true)
public class RedisZSetBO {

    private Object value;

    private Double score;
}
