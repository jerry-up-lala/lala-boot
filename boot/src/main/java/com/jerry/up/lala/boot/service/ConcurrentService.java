package com.jerry.up.lala.boot.service;


import com.jerry.up.lala.boot.bo.ConcurrentBO;
import com.jerry.up.lala.boot.vo.ConcurrentVO;
import com.jerry.up.lala.framework.core.redis.RedisLogInfoBO;

/**
 * <p>Description: 多线程样例service
 *
 * @author FMJ
 * @date 2022/3/29 22:54
 */
public interface ConcurrentService {

    /**
     * <p>Description:请求多线程
     *
     * @param id           日志ID
     * @param concurrentVO 线程创建参数
     * @author FMJ
     * @date 2022/3/29 17:46
     */
    void run(String id, ConcurrentVO concurrentVO);

    /**
     * <p>Description:查询多线程执行记录
     *
     * @param id 日志ID
     * @return 执行记录
     * @author FMJ
     * @date 2022/3/29 17:46
     */
    RedisLogInfoBO<ConcurrentBO> log(String id);

}
