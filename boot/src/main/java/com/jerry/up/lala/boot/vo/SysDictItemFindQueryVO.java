package com.jerry.up.lala.boot.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>Description: 字典项 查询
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
public class SysDictItemFindQueryVO {

    /**
     * 字典标识
     */
    private String dictKey;

    /**
     * 值列表
     */
    private List<String> valueList;

}
