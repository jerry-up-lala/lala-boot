package com.jerry.up.lala.boot.listener;

import com.jerry.up.lala.boot.bo.MqBO;
import com.jerry.up.lala.boot.constant.MqConstant;
import com.jerry.up.lala.boot.service.MqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 消费消息
 *
 * @author FMJ
 * @date 2023/9/14 14:51
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MqConstant.LALA_BOOT_CONSUMER, topic = MqConstant.DEMO_TOPIC)
public class MqDemoTopicListener implements RocketMQListener<MqBO> {

    @Autowired
    private MqService mqService;

    @Override
    public void onMessage(MqBO mqBO) {
        mqService.consumer(mqBO);
    }
}
