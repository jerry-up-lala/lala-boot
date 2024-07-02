package com.jerry.up.lala.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>Description: 应用启动类
 *
 * DATA_PATH=/Users/jerry/root/root/data/lala;NACOS_CONFIG_HOST=192.168.27.240:18848;NACOS_CONFIG_ENV=env.192.168.27.240.yaml
 * DATA_PATH=/Users/jerry/root/root/data/lala;NACOS_CONFIG_HOST=192.168.35.61:8848;NACOS_CONFIG_ENV=env.192.168.35.61.yaml
 * @author FMJ
 * @date 2023/8/9 14:12
 */
@SpringBootApplication
@ComponentScan("com.jerry.up.lala")
@Slf4j
public class BootApp {

    public static void main(String[] args) {
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(BootApp.class, args);
    }

}
