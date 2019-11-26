package com.jibug.cetty.sample.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @ClassName ScheduleConfig
 * @Description TODO
 * @Author jb.zhou
 * @Date 2019/9/26
 * @Version 1.0
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
    @Bean(destroyMethod="shutdownNow")
    public ScheduledExecutorService taskExecutors() {
        return Executors.newScheduledThreadPool(10);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //参数传入一个线程池
        scheduledTaskRegistrar.setScheduler(taskExecutors());
    }

}
