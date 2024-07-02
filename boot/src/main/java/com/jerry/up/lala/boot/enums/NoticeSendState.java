package com.jerry.up.lala.boot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 通知发送状态
 * notice.send_state
 *
 * @author FMJ
 * @date 2023/12/14 20:26
 */
@Getter
@AllArgsConstructor
public enum NoticeSendState {

    /**
     * 0-未发送，1-已发送
     */
    UNSENT(0),

    SENT(1);

    private final Integer value;

}
