package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.vo.NoticeUserAllQueryVO;
import com.jerry.up.lala.boot.vo.NoticeUserQueryVO;
import com.jerry.up.lala.framework.common.annotation.DataFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>Description: 通知接收情况查询DTO
 *
 * @author FMJ
 * @date 2024/1/10 11:52
 */
@Data
@Accessors(chain = true)
public class NoticeUserQueryDTO {

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

    @DataFormat(sourceFieldName = NoticeUserQueryVO.Fields.sendTimeRang, listIndex = 0, dateType = 2)
    private Date sendTimeStart;

    @DataFormat(sourceFieldName = NoticeUserQueryVO.Fields.sendTimeRang, listIndex = 1, dateType = 2)
    private Date sendTimeEnd;

    /**
     * 通知ID
     */
    private Long noticeId;

    /**
     * 账号ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 阅读状态【0-未发送，1-未读，2-已读】
     */
    private Integer readState;

    @DataFormat(sourceFieldName = NoticeUserAllQueryVO.Fields.readTimeRang, listIndex = 0, dateType = 2)
    private Date readTimeStart;

    @DataFormat(sourceFieldName = NoticeUserAllQueryVO.Fields.readTimeRang, listIndex = 1, dateType = 2)
    private Date readTimeEnd;

    private Long limit;
}
