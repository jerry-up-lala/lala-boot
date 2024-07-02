package com.jerry.up.lala.boot.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 预览BO
 *
 * @author FMJ
 * @date 2024/2/14 21:11
 */
@Data
@Accessors(chain = true)
public class GenPreviewBO {

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件名
     */
    private String name;

    /**
     * 代码
     */
    private String code;

}
