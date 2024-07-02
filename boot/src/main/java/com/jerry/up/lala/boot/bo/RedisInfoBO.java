package com.jerry.up.lala.boot.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>Description: redis 信息 vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RedisInfoBO extends RedisKeyBO {

    private Object value;

}
