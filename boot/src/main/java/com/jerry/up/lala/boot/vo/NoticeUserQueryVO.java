package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.dto.NoticeUserQueryDTO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import com.jerry.up.lala.framework.common.model.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * <p>Description: 通知管理#用户通知#查询VO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@DataBean(targetTypes = {NoticeUserQueryDTO.class})
public class NoticeUserQueryVO extends PageQuery {

    /**
     * 标题
     */
    private String title;

    /**
     * 类型【1-消息，2-公告】
     */
    private Integer noticeType;

    /**
     * 阅读状态【0-未发送，1-未读，2-已读】
     */
    private Integer readState;

    /**
     * 发送时间
     */
    private List<String> sendTimeRang;

    /**
     * 阅读时间
     */
    private List<String> readTimeRang;

}
