package com.jibug.cetty.sample.service.support;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jibug.cetty.core.Bootstrap;
import com.jibug.cetty.core.Payload;
import com.jibug.cetty.core.Seed;
import com.jibug.cetty.core.handler.ProcessHandlerAdapter;
import com.jibug.cetty.core.handler.ReduceHandlerAdapter;
import com.jibug.cetty.core.log.LogUtil;
import com.jibug.cetty.core.net.Proxy;
import com.jibug.cetty.sample.constants.MainConstants;
import com.jibug.cetty.sample.dao.MlGoodsBrDao;
import com.jibug.cetty.sample.dao.MlGoodsMxDao;
import com.jibug.cetty.sample.service.ProxyPoolService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author heyingcai
 * @date 2019-07-02 17:43
 */
@Component
public class CrawlerService {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    @Resource
    private TaskService taskService;

    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private MlGoodsMxDao mlGoodsMxDao;
    @Autowired
    private MlGoodsBrDao mlGoodsBrDao;

    @Autowired
    private ProxyPoolService proxyPoolService;

    public void start() {
        JSONArray taskObject = taskService.getTaskObject();

        if (taskObject.size() != 0) {
            logger.info("获取到待抓取任务 {}", taskObject.toJSONString());
            for (int i = 0; i < taskObject.size(); i++) {
                JSONObject task = taskObject.getJSONObject(i);
                if (task.getInteger("status") == 0) {
                    continue;
                }

                Seed seed = new Seed(task.getString("url"));
                seed.putAttach("via", task.getString("via"));
                seed.putAttach("pageLimit", task.getString("pageLimit"));

                String proxyIp = "";
                Proxy proxy = null;
                try {
                    proxyIp = proxyPoolService.getProxyPoolIp();
                    if(StringUtils.isNotEmpty(proxyIp)){
                        Object document = Configuration.defaultConfiguration().jsonProvider().parse(proxyIp);

                        String ip = JsonPath.read(document, "$.data[0].ip");
                        Integer port = JsonPath.read(document, "$.data[0].port");
                        String scheme = JsonPath.read(document, "$.data[0].type");

                        proxy = new Proxy(ip,port,scheme);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Payload payload = null;
                if(proxy == null){
                    payload = Payload.custom();
                    LogUtil.error("无IP代理");
//                    return;
                }else{
                    payload = Payload.custom()
                            .setProxy(proxy);
                }

//                mlGoodsMxDao.deleteAll();
//                mlGoodsBrDao.deleteAll();

                //定义爬虫引导程序
                Bootstrap bootstrap = Bootstrap.me()
                        .startSeed(seed)
                        .addHandler(applicationContext.getBean(task.getString("pageHandler"), ProcessHandlerAdapter.class))
                        .addHandler(applicationContext.getBean(task.getString("pageReducer"), ReduceHandlerAdapter.class))
                        .setThreadNum(20)
                        .setPayload(
                                payload.setConnectTimeout(MainConstants.TIME_OUT)
                                .addHeader("User-Agent", MainConstants.UA_WIN))
                      .isAsync(true);
//                        .isAsync(false);

                bootstrap.start();
            }
        }


    }

}
