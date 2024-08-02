package com.jerry.up.lala.boot.constant;

import com.jerry.up.lala.framework.common.constant.CommonConstant;

/**
 * <p>Description: 通知管理常量
 *
 * @author FMJ
 * @date 2023/9/14 16:52
 */
public class MqConstant {

    public static final String LALA_BOOT_CONSUMER = "lala_boot_consumer";

    /**
     * 与 log.mq配置项对应
     */
    public static final String LOG_REQUEST_TOPIC = "lala_boot_log_request_topic";

    public static final String LOG_REQUEST_TOPIC_CONSUMER_GROUP = LALA_BOOT_CONSUMER + "_" + LOG_REQUEST_TOPIC;

    public static final String DEMO_TOPIC = "demo_topic";

    public static final String DEMO_TOPIC_CONSUMER_GROUP = LALA_BOOT_CONSUMER + "_" + DEMO_TOPIC;

}
