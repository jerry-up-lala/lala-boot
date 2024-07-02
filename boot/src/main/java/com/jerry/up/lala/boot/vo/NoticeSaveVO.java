package com.jerry.up.lala.boot.vo;

import com.jerry.up.lala.boot.entity.Notice;
import com.jerry.up.lala.framework.core.data.DataBean;
import lombok.Data;

import java.util.List;

/**
 * <p>Description: 通知保存VO
 *
 * @author FMJ
 * @date 2024/1/9 17:20
 */
@Data
@DataBean(targetTypes = {Notice.class})
public class NoticeSaveVO {

    /**
     * 标题
     */
    private String title;

    /**
     * 类型【1-消息，2-公告】
     */
    private Integer noticeType;

    /**
     * 通知内容
     */
    private String html;

    /**
     * 接收账号ID列表
     */
    private List<String> userIds;

}
