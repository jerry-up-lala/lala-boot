package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>Description: 通知管理VO
 *
 * @author FMJ
 * @date 2024/1/10 14:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeInfoVO extends NoticeVO {

    /**
     * 内容
     */
    private String html;

    /**
     * 接收账号ID列表
     */
    private List<String> userIds;
}
