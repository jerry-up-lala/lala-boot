package com.jerry.up.lala.boot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 通知类型
 * notice.notice_type
 *
 * @author FMJ
 * @date 2023/12/14 20:26
 */
@Getter
@AllArgsConstructor
public enum NoticeType {

    /**
     * 1-消息，2-公告
     */
    INFO(1),

    NOTICE(2);

    private final Integer value;

    public static NoticeType fromValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (NoticeType type : NoticeType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
