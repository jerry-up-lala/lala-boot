package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.boot.vo.NoticeInfoVO;
import com.jerry.up.lala.boot.vo.NoticeVO;
import com.jerry.up.lala.framework.core.common.Entity;
import com.jerry.up.lala.framework.core.data.DataBean;
import com.jerry.up.lala.framework.core.data.DataFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 通知表
 *
 * @author FMJ
 * @TableName notice
 */
@TableName(value = "notice")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@DataBean(targetTypes = {NoticeVO.class, NoticeInfoVO.class})
public class Notice extends Entity {
    /**
     * 消息ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 集团ID
     */
    private String tenantId;

    /**
     * 标题
     */
    private String title;

    /**
     * 通知内容
     */
    @DataFormat(maxLength = 10000)
    private String html;

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
    private Date sendTime;

}