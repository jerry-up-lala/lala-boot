package com.jerry.up.lala.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.framework.boot.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 通知记录表
 *
 * @author FMJ
 * @TableName notice_user
 */
@TableName(value = "notice_user")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class NoticeUser extends Entity {
    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 集团ID
     */
    private String tenantId;

    /**
     * notice.id
     */
    private Long noticeId;

    /**
     * user.id
     */
    private String userId;

    /**
     * 阅读状态【0-未发送，1-未读，2-已读】
     */
    private Integer readState;

    /**
     * 阅读时间
     */
    private Date readTime;

}