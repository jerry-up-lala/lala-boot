package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>Description: 用户通知列表VO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
public class NoticeUserVO {

    /**
     * 消息记录ID
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
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date sendTime;

    /**
     * 阅读状态【0-未发送，1-未读，2-已读】
     */
    private Integer readState;

    /**
     * 阅读时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date readTime;

}
