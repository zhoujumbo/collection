package com.jibug.cetty.sample.schedule;

import com.basic.support.commons.business.logger.LogUtil;
import com.jibug.cetty.sample.schedule.tasks.MlTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 多线程 定时任务
 */
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync
public class MultithreadScheduleTask {

    @Autowired
    private MlTask mlTask;

    @Scheduled(fixedDelay = 50*1000)
    public void task1(){
        try {
            LogUtil.warn("saveMx run");
            mlTask.saveMx();
        } catch (Exception e) {
            LogUtil.error("saveMx：任务执行错误");
        }

    }

    @Scheduled(fixedDelay = 70*1000)
    public void task2(){
        try {
            LogUtil.warn("saveBr run");
            mlTask.saveBr();
        } catch (Exception e) {
            LogUtil.error("saveBr：任务执行错误");
        }

    }





}
