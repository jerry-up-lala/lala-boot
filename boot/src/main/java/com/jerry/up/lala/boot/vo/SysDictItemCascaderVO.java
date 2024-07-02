package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 字典项级联选择
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
@Accessors(chain = true)
public class SysDictItemCascaderVO {

    /**
     * 字典项ID
     */
    private Long value;

    /**
     * 展示名
     */
    private String label;


    private List<SysDictItemCascaderVO> children;
}
