package com.jerry.up.lala.boot.listener;

import com.jerry.up.lala.boot.constant.MqConstant;
import com.jerry.up.lala.boot.service.SysLogRequestService;
import com.jerry.up.lala.framework.core.common.CommonConstant;
import com.jerry.up.lala.framework.core.api.ApiLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 请求日志消息
 *
 * @author FMJ
 * @date 2023/9/14 14:51
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MqConstant.LALA_BOOT_CONSUMER, topic = CommonConstant.REQUEST_LOG_TOPIC)
public class LogRequestTopicListener implements RocketMQListener<ApiLog> {

    @Autowired
    private SysLogRequestService sysLogRequestService;

    @Override
    public void onMessage(ApiLog apiLog) {
        sysLogRequestService.save(apiLog);
    }
}
