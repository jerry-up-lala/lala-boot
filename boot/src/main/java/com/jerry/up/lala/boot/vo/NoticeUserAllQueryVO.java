package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.dto.NoticeUserQueryDTO;
import com.jerry.up.lala.framework.core.common.PageQuery;
import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * <p>Description: 通知管理#接收记录#查询VO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@DataBean(targetTypes = {NoticeUserQueryDTO.class})
public class NoticeUserAllQueryVO extends PageQuery {

    /**
     * 通知ID
     */
    private Long noticeId;

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

    /**
     * 阅读时间
     */
    private List<String> readTimeRang;

}
