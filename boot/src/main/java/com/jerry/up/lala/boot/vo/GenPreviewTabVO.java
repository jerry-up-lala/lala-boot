package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 代码预览tab
 *
 * @author FMJ
 * @date 2024/02/20 09:45
 */
@Data
@Accessors(chain = true)
public class GenPreviewTabVO {

    /**
     * 完整路径
     */
    private String key;

    /**
     * 标题
     */
    private String title;

    /**
     * 代码
     */
    private String code;
}
