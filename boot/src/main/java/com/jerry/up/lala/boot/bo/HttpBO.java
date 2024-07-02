package com.jerry.up.lala.boot.bo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * http 请求信息
 *
 * @author FMJ
 */
@Data
@Accessors(chain = true)
public class HttpBO {

    /**
     * 线程名称
     */
    private String threadName;

    /**
     * 发送结果
     */
    private String sendResult;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date sendDateTime;

}