package com.jerry.up.lala.boot.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 字典项校验
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
@Accessors(chain = true)
public class SysDictItemVerifyBO {

    /**
     * 字典项ID
     */
    private Long id;

    /**
     * 字典父ID
     */
    private Long parentId;

    /**
     * 展示名/值
     */
    private String value;


}
