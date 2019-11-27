package com.jibug.cetty.sample.service.support;

import com.alibaba.fastjson.JSONArray;
import com.basic.support.commons.business.logger.LogUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author heyingcai
 * @date 2019-07-02 17:41
 */
@Component
public class TaskService {

    @Value("classpath:crawler.json")
    private Resource resource;

    private JSONArray taskObject;

    @PostConstruct
    private void parse() {
        try {
            String jsonString = IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8").toString());
            taskObject = JSONArray.parseArray(jsonString);
        } catch (IOException e) {
            LogUtil.error("crawler.json读取任务错误");
        }
    }

    public JSONArray getTaskObject() {
        return taskObject;
    }
}
