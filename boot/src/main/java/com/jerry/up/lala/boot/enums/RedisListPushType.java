package com.jerry.up.lala.boot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: redis list push 类型
 *
 * @author FMJ
 * @date 2023/11/10 10:18
 */
@Getter
@AllArgsConstructor
public enum RedisListPushType {

    /**
     * redis list 加入 Element 类型
     */
    TAIL(1), HEAD(2);

    private final Integer type;
}
