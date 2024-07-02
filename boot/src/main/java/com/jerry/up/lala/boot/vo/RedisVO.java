package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: redis vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@Accessors(chain = true)
public class RedisVO {

    /**
     * 名称
     */
    private String name;

    /**
     * 有效期【单位:秒】
     */
    private Long timeOut;
}
