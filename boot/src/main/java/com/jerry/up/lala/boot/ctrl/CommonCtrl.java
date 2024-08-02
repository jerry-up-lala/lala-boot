package com.jerry.up.lala.boot.ctrl;

import cn.hutool.core.util.IdUtil;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.r.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 公共控制层
 *
 * @author FMJ
 * @date 2023/11/3 09:27
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonCtrl {

    @GetMapping("/snow-id")
    @Api(value = "公共接口-获取雪花ID")
    public R snowId() {
        String snowId = IdUtil.getSnowflakeNextIdStr();
        return R.succeed(snowId);
    }

    @GetMapping("/exception")
    @Api(value = "公共接口-异常样例")
    public R exception() {
        throw ServiceException.error();
    }

}
