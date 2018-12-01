package com.ruoyi;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.time.Duration;
import java.time.Instant;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.ruoyi.*.mapper")
public class RuoYiApplication {

    private static final Logger log = LoggerFactory.getLogger(RuoYiApplication.class);

    public static void main(String[] args) {
        Instant inst1 = Instant.now();
        SpringApplication.run(RuoYiApplication.class, args);
        log.info("启动成功!耗时:{}秒", Duration.between(inst1, Instant.now()).getSeconds());
    }

}