package com.jerry.up.lala.boot.config;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.jerry.up.lala.framework.core.common.CommonProperties;
import com.jerry.up.lala.framework.core.satoken.SaTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 登录拦截
 *
 * @author FMJ
 * @date 2023/9/4 15:53
 */
@Slf4j
@Configuration
public class SaTokenFilterConfig {

    @Autowired
    private CommonProperties commonProperties;

    /**
     * 注册 [sa-token全局过滤器]
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                .addInclude("/**")
                .setAuth(auth -> SaRouter.match("/**")
                        .notMatch(commonProperties.getOpenUrls())
                        .check(StpUtil::checkLogin))
                .setError(SaTokenUtil::loginRError);
    }
}
