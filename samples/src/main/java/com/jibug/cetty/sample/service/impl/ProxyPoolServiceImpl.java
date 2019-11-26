package com.jibug.cetty.sample.service.impl;

import com.jibug.cetty.sample.service.ProxyPoolService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProxyPoolServiceImpl implements ProxyPoolService {

    @Override
    public String getProxyPoolIp() throws IOException {

        Response response = null;
//        Request request = Request.Get("http://localhost:8081/proxyIp");
        Request request = Request.Get("http://47.111.104.80/proxyIp");
        request.connectTimeout(5000);
        try {
            response = request.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json = response.returnContent().asString();
        if (StringUtils.isNotEmpty(json)) {
            return json;
        }
        return "";
    }


}
