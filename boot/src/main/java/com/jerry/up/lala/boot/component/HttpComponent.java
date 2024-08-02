package com.jerry.up.lala.boot.component;

import cn.hutool.http.HttpUtil;
import com.jerry.up.lala.boot.bo.HttpBO;
import com.jerry.up.lala.framework.boot.redis.RedisLogAddBO;
import com.jerry.up.lala.framework.boot.redis.RedisLogComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>Description: 请求组件
 *
 * @author FMJ
 * @date 2023/11/1 17:53
 */
@Component
public class HttpComponent {

    @Autowired
    private RedisLogComponent<HttpBO> redisLogComponent;

    @Async("http-executor")
    public void get(String id, String httpUrl) {
        String sendResult = HttpUtil.get(httpUrl);
        HttpBO httpBO = new HttpBO().setThreadName(Thread.currentThread().getName())
                .setSendResult(sendResult).setSendDateTime(new Date());

        RedisLogAddBO<HttpBO> redisLogAddBO = new RedisLogAddBO<HttpBO>().setInfo(httpBO);
        redisLogAddBO.setValue(id);
        redisLogComponent.add(redisLogAddBO);
    }

    @Bean("http-executor")
    public Executor httpExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程
        executor.setCorePoolSize(10);
        // 最大线程
        executor.setMaxPoolSize(20);
        // 缓冲队列
        executor.setQueueCapacity(200);
        // 允许线程的空闲时间
        executor.setKeepAliveSeconds(60);
        // 线程池名的前缀
        executor.setThreadNamePrefix("http-executor-");
        // 用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        // 这样这些异步任务的销毁就会先于Redis线程池的销毁
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
