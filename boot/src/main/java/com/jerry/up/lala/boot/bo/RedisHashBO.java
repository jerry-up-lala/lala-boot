package com.jerry.up.lala.boot.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: redis hash
 *
 * @author FMJ
 * @date 2023/11/10 09:55
 */
@Data
@Accessors(chain = true)
public class RedisHashBO {

    private String field;

    private Object value;
}
