package com.jerry.up.lala.boot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 用户消息阅读状态
 * notice_user.read_state
 *
 * @author FMJ
 * @date 2023/12/14 20:26
 */
@Getter
@AllArgsConstructor
public enum NoticeUserReadState {

    /**
     * 0-未发送，1-未读，2-已读
     */
    UNSENT(0),

    UNREAD(1),

    READ(2);

    private final Integer value;

}
