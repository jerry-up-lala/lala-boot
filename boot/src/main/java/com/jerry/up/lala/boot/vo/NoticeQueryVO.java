package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.dto.NoticeQueryDTO;
import com.jerry.up.lala.framework.core.common.PageQuery;
import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * <p>Description: 通知管理查询VO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@DataBean(targetTypes = {NoticeQueryDTO.class})
public class NoticeQueryVO extends PageQuery {

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

    /**
     * 发送时间
     */
    private List<String> sendTimeRang;

}
