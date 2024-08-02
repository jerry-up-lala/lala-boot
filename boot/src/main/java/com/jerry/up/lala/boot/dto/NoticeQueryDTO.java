package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.vo.NoticeQueryVO;
import com.jerry.up.lala.framework.common.annotation.DataFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>Description: 通知管理查询DTO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
public class NoticeQueryDTO {

    /**
     * 标题
     */
    private String title;

    /**
     * 类型【1-消息，2-公告】
     */
    private Integer noticeType;

    /**
     * 发送状态【0-未发送，1-已发送】
     */
    private Integer sendState;

    @DataFormat(sourceFieldName = NoticeQueryVO.Fields.sendTimeRang, listIndex = 0, dateType = 2)
    private Date sendTimeStart;

    @DataFormat(sourceFieldName = NoticeQueryVO.Fields.sendTimeRang, listIndex = 1, dateType = 2)
    private Date sendTimeEnd;

}
