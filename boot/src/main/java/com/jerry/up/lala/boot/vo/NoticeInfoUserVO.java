package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 用户通知列表详情VO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeInfoUserVO extends NoticeUserVO {

    /**
     * 内容
     */
    private String html;

}
