package com.ruoyi.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
@Slf4j
public class RyTask {
    public void ryParams(String params) {
        log.info("执行有参方法:"+ params);
    }

    public void ryNoParams() {
        log.info("执行无参方法:");
    }
}
