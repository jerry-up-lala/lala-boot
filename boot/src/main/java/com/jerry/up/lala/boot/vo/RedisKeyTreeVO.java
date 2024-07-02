package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: redis key tree
 *
 * @author FMJ
 * @date 2023/11/7 09:45
 */
@Data
@Accessors(chain = true)
public class RedisKeyTreeVO {

    private String key;

    private String title;

    private Long count;

    private List<RedisKeyTreeVO> children;
}
