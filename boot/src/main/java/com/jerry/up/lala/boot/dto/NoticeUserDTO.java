package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.entity.NoticeUser;
import com.jerry.up.lala.boot.vo.NoticeUserAllVO;
import com.jerry.up.lala.boot.vo.NoticeInfoUserVO;
import com.jerry.up.lala.boot.vo.NoticeUserVO;
import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>Description: 通知管理接收记录DTO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DataBean(targetTypes = {NoticeUserAllVO.class, NoticeUserVO.class, NoticeInfoUserVO.class})
public class NoticeUserDTO extends NoticeUser {

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String html;

    /**
     * 类型【1-消息，2-公告】
     */
    private Integer noticeType;

    /**
     * 发送时间
     */
    private Date sendTime;

}
