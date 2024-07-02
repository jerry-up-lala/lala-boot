package com.jerry.up.lala.boot.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Description: 多线程执行日志数据
 *
 * @author FMJ
 * @date 2022/3/30 11:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcurrentBO {

    /**
     * 线程名称
     */
    private String threadName;
    /**
     * 执行时间
     */
    private String runDateTime;
}
