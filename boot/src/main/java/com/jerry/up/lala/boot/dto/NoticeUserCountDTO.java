package com.jerry.up.lala.boot.dto;

import lombok.Data;

/**
 * <p>Description: 通知管理接收记录DTO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
public class NoticeUserCountDTO {

    /**
     * 通知类型(1-消息，2-公告)
     *
     * @see com.jerry.up.lala.boot.enums.NoticeType;
     */
    private Integer noticeType;

    /**
     * 订单数量
     */
    private Long count;


}
