package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.access.AccessConstant;
import com.jerry.up.lala.boot.bo.ConcurrentBO;
import com.jerry.up.lala.boot.service.ConcurrentService;
import com.jerry.up.lala.boot.vo.ConcurrentVO;
import com.jerry.up.lala.framework.core.common.Api;
import com.jerry.up.lala.framework.core.common.R;
import com.jerry.up.lala.framework.core.redis.RedisLogInfoBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: 并发样例
 *
 * @author FMJ
 * @date 2023/11/1 15:31
 */
@Slf4j
@RestController
@RequestMapping("/concurrent")
public class ConcurrentCtrl {

    @Autowired
    private ConcurrentService concurrentService;

    /**
     * <p>Description:请求多线程
     *
     * @return 调用结果
     * @author FMJ
     * @date 2022/3/29 17:46
     */
    @PostMapping("/{id}")
    @Api(value = "线程池样例-发送", accessCodes = AccessConstant.CONCURRENT)
    public R run(@RequestBody @PathVariable("id") String id, @RequestBody ConcurrentVO concurrentVO) {
        concurrentService.run(id, concurrentVO);
        return R.succeed(null);
    }

    /**
     * <p>Description:查询多线程执行结果
     *
     * @return 调用结果
     * @author FMJ
     * @date 2022/3/29 17:46
     */
    @GetMapping("/{id}")
    @Api(value = "线程池样例-日志记录", accessCodes = AccessConstant.CONCURRENT)
    public R log(@PathVariable("id") String id) {
        RedisLogInfoBO<ConcurrentBO> result = concurrentService.log(id);
        return R.succeed(result);
    }

}
