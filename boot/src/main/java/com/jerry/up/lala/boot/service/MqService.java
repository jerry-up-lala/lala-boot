package com.jerry.up.lala.boot.service;

import com.jerry.up.lala.boot.bo.MqBO;
import com.jerry.up.lala.boot.vo.MqVO;
import com.jerry.up.lala.framework.core.redis.RedisLogInfoBO;

import java.util.List;

/**
 * <p>Description: 通知管理service
 *
 * @author FMJ
 * @date 2023/9/14 11:21
 */
public interface MqService {

    void producer(String id, MqVO mqVO);

    void consumer(MqBO mqBO);

    RedisLogInfoBO<MqBO> get(String id);

}
