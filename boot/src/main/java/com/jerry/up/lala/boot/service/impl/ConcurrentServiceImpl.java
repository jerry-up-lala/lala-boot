package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.jerry.up.lala.boot.bo.ConcurrentBO;
import com.jerry.up.lala.boot.enums.BlockingQueueType;
import com.jerry.up.lala.boot.enums.RejectedExecutionType;
import com.jerry.up.lala.boot.service.ConcurrentService;
import com.jerry.up.lala.boot.vo.ConcurrentVO;
import com.jerry.up.lala.framework.core.common.DataBody;
import com.jerry.up.lala.framework.core.exception.ServiceException;
import com.jerry.up.lala.framework.core.redis.RedisLogAddBO;
import com.jerry.up.lala.framework.core.redis.RedisLogComponent;
import com.jerry.up.lala.framework.core.redis.RedisLogInfoBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.*;

/**
 * <p>Description: 多线程样例service
 *
 * @author FMJ
 * @date 2022/3/29 22:54
 */
@Service
@Slf4j
public class ConcurrentServiceImpl implements ConcurrentService {

    @Autowired
    private RedisLogComponent<ConcurrentBO> redisLogComponent;

    /**
     * <p>Description:请求多线程
     *
     * @param id           日志ID
     * @param concurrentVO 请求参数
     * @author FMJ
     * @date 2022/3/29 17:46
     */
    @Override
    public void run(String id, ConcurrentVO concurrentVO) {
        BlockingQueue<Runnable> blockingQueue = getBlockingQueue(concurrentVO.getWorkQueueType(), concurrentVO.getWorkQueueSize());
        RejectedExecutionType rejectedExecutionType = RejectedExecutionType.fromValue(concurrentVO.getRejectedExecutionType());
        if (blockingQueue == null || rejectedExecutionType == null) {
            throw ServiceException.args();
        }
        try {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(concurrentVO.getCorePoolSize(), concurrentVO.getMaximumPoolSize(),
                    concurrentVO.getKeepAliveTime(), TimeUnit.MILLISECONDS, blockingQueue, rejectedExecutionType.getHandler());
            for (int i = 0; i < concurrentVO.getRunTotal(); i++) {
                Future<ConcurrentBO> future = threadPoolExecutor.submit(this::concurrentRun);
                ConcurrentBO futureResult = future.get();
                RedisLogAddBO<ConcurrentBO> redisLogAddBO = new RedisLogAddBO<ConcurrentBO>().setInfo(futureResult);
                redisLogAddBO.setValue(id);
                redisLogComponent.add(redisLogAddBO);
            }
        } catch (Exception e) {
            throw ServiceException.error(e);
        }
    }

    /**
     * <p>Description:查询多线程执行记录
     *
     * @param id 日志ID
     * @author FMJ
     * @date 2022/3/29 17:46
     */
    @Override
    public RedisLogInfoBO<ConcurrentBO> log(String id) {
        return redisLogComponent.get(new DataBody<>(id));
    }

    private ConcurrentBO concurrentRun() {
        String threadName = Thread.currentThread().getName();
        String runDateTime = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MS_FORMAT);
        return new ConcurrentBO(threadName, runDateTime);
    }

    private BlockingQueue<Runnable> getBlockingQueue(Integer blockingQueueType, Integer blockingQueueSize) {
        if (BlockingQueueType.LINKED_BLOCKING_QUEUE.getValue().equals(blockingQueueType)) {
            return new LinkedBlockingQueue<>(blockingQueueSize);
        }
        if (BlockingQueueType.ARRAY_BLOCKING_QUEUE.getValue().equals(blockingQueueType)) {
            return new ArrayBlockingQueue<>(blockingQueueSize);
        }
        if (BlockingQueueType.LINKED_BLOCKING_DEQUE.getValue().equals(blockingQueueType)) {
            return new LinkedBlockingDeque<>(blockingQueueSize);
        }
        if (BlockingQueueType.PRIORITY_BLOCKING_QUEUE.getValue().equals(blockingQueueType)) {
            return new PriorityBlockingQueue<>(blockingQueueSize);
        }
        if (BlockingQueueType.LINKED_TRANSFER_QUEUE.getValue().equals(blockingQueueType)) {
            return new LinkedTransferQueue<>();
        }
        if (BlockingQueueType.SYNCHRONOUS_QUEUE.getValue().equals(blockingQueueType)) {
            return new SynchronousQueue<>();
        }
        return null;
    }

}
