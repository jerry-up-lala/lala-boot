package com.jerry.up.lala.boot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>Description: 通知接收情况VO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
public class NoticeUserAllVO {

    /**
     * 消息ID
     */
    private Long id;

    /**
     * user.id
     */
    private String userId;

    /**
     * 阅读状态【0-未发送，1-未读，2-已读】
     */
    private Integer readState;

    /**
     * 阅读时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date readTime;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 姓名
     */
    private String realName;

}
