package com.jerry.up.lala.boot.vo;

import lombok.Data;

/**
 * <p>Description: 多线程请求参数
 *
 * @author FMJ
 * @date 2022/3/29 22:52
 */
@Data
public class ConcurrentVO {

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    private Integer maximumPoolSize;

    /**
     * 非核心线程数等待时间(毫秒)
     */
    private Long keepAliveTime;

    /**
     * 达到核心线程数存放队列类型
     * @see com.jerry.up.lala.boot.enums.BlockingQueueType
     */
    private Integer workQueueType;

    /**
     * 队列大小
     */
    private Integer workQueueSize;

    /**
     * 饱和策略
     * @see com.jerry.up.lala.boot.enums.RejectedExecutionType
     */
    private Integer rejectedExecutionType;

    /**
     * 执行数量
     */
    private Integer runTotal;

}
