package com.jerry.up.lala.boot.bo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * mq 信息
 *
 * @author FMJ
 */
@Data
@Accessors(chain = true)
public class MqBO {

    /**
     * 日志ID
     */
    private String id;

    /**
     * 消费线程名称
     */
    private String consumerThreadName;

    /**
     * 发送结果
     */
    private String info;

    /**
     * 生产消息时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date producerDateTime;

    /**
     * 消费消息时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date consumerDateTime;

}