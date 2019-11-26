package com.jibug.cetty.sample.service.impl;

import com.jibug.cetty.core.log.LogUtil;
import com.jibug.cetty.sample.service.TaskService;
import com.jibug.cetty.sample.service.support.CrawlerService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private CrawlerService crawlerService;

    @Override
    @Async("asyncServiceExecutor")
    public void aSyncTaskRun(){
        LogUtil.info("异步 服务 开启 aSyncTaskRun");
        crawlerService.start();
    }
}
