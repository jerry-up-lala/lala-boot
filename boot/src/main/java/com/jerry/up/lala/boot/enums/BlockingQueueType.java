package com.jerry.up.lala.boot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 阻塞队列类型
 *
 * @author FMJ
 * @date 2022/3/29 23:19
 */
@Getter
@AllArgsConstructor
public enum BlockingQueueType {

    /**
     * 有界阻塞队列,默认大小为Integer.MAX_VALUE、链式结构(链接节点) FIFO
     *
     * @see java.util.concurrent.Executors#newFixedThreadPool 可重用固定线程数
     * @see java.util.concurrent.Executors#newSingleThreadExecutor 使用单个worker
     */
    LINKED_BLOCKING_QUEUE(1),
    /**
     * 有界阻塞队列、数组结构 FIFO
     */
    ARRAY_BLOCKING_QUEUE(2),
    /**
     * 有界阻塞双端队列,默认大小为Integer.MAX_VALUE、链式结构(链接节点)
     */
    LINKED_BLOCKING_DEQUE(3),
    /**
     * 有界阻塞队列,默认大小为11,优先级
     */
    PRIORITY_BLOCKING_QUEUE(4),

    /**
     * 无界阻塞队列、由链表结构组成
     */
    LINKED_TRANSFER_QUEUE(5),
    /**
     * 不存储元素的阻塞队列,每个插入操作必须等待一个移出操作,默认非公平
     *
     * @see java.util.concurrent.Executors#newCachedThreadPool 根据需要创建新线程
     */
    SYNCHRONOUS_QUEUE(6),
    /**
     * 无界阻塞队列,堆实现优先级【ScheduledThreadPoolExecutor内部类】
     *
     * @see java.util.concurrent.Executors#newScheduledThreadPool 周期性执行线程池
     */
    DELAYED_WORK_QUEUE(7);

    private final Integer value;

}
