package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>Description: 通知管理VO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
public class NoticeVO {

    /**
     * 消息ID
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型【1-消息，2-公告】
     */
    private Integer noticeType;

    /**
     * 发送状态 【0-未发送，1-已发送】
     */
    private Integer sendState;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date sendTime;

}
