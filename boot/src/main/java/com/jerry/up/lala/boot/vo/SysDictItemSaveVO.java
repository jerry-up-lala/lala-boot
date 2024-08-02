package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.entity.SysDictItem;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;

/**
 * <p>Description: 字典项
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
@DataBean(targetTypes = {SysDictItem.class})
public class SysDictItemSaveVO {

    /**
     * 字典父ID
     */
    private Long parentId;

    /**
     * 展示名
     */
    private String label;

    /**
     * 值
     */
    private String value;

    /**
     * 排序号(值越高，越靠后)
     */
    private Integer sortOrder;

}
