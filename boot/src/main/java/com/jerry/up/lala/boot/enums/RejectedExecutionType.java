package com.jerry.up.lala.boot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>Description: 饱和策略
 *
 * @author FMJ
 * @date 2022/3/30 09:27
 */
@Getter
@AllArgsConstructor
public enum RejectedExecutionType {

    /**
     * 直接抛出异常，默认策略
     */
    ABORT_POLICY(1, new ThreadPoolExecutor.AbortPolicy()),
    /**
     * 用调用者所在的线程来执行任务
     */
    CALLER_RUNS_POLICY(2, new ThreadPoolExecutor.CallerRunsPolicy()),
    /**
     * 丢弃阻塞队列中靠最前的任务，并执行当前任务
     */
    DISCARD_OLDEST_POLICY(3, new ThreadPoolExecutor.DiscardOldestPolicy()),
    /**
     * 直接丢弃任务
     */
    DISCARD_POLICY(4, new ThreadPoolExecutor.DiscardPolicy());

    private final Integer value;

    private final RejectedExecutionHandler handler;


    public static RejectedExecutionType fromValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (RejectedExecutionType type : RejectedExecutionType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

}
