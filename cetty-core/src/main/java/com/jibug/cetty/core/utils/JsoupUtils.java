package com.jibug.cetty.core.utils;

import com.jibug.cetty.core.log.LogUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * Jsoup 工具类
 * Created by Silence on 2017/1/25.
 */

public class JsoupUtils {
    private static final String UA_PHONE = "Mozilla/5.0 (Linux; Android 4.3; Nexus 10 Build/JSS15Q) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Safari/537.36";
    private static final String UA_PC = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36";

    private static final int TIME_OUT = 10 * 1000;

    private static final String ERROR_DESC = "网址请求失败：";

    public static Document getDocWithPC(String url) {
        try {
            return Jsoup.connect(url).userAgent(UA_PC).timeout(TIME_OUT).ignoreContentType(true).get();
        } catch (IOException e) {
            LogUtil.error(ERROR_DESC + url);
            throw new RuntimeException(ERROR_DESC + url);
        }
    }

    public static Document getDocWithPhone(String url) {
        try {
            return Jsoup.connect(url).userAgent(UA_PHONE).timeout(TIME_OUT).ignoreContentType(true).validateTLSCertificates(false).get();
        } catch (IOException e) {
            LogUtil.error(ERROR_DESC + url);
            throw new RuntimeException(ERROR_DESC + url);
        }
    }

    public static Document getDocWithPhone(String url, String cookie) {
        try {
            return Jsoup.connect(url).userAgent(UA_PHONE).timeout(TIME_OUT).header("Cookie", cookie).ignoreContentType(true).get();
        } catch (IOException e) {
            LogUtil.error(ERROR_DESC + url);
            throw new RuntimeException(ERROR_DESC + url);
        }
    }


    public static Document getJsoupDocGet(String url) {
        //三次试错
        final int MAX = 10;
        int time = 0;
        Document doc = null;
        while (time < MAX) {
            try {
                doc = Jsoup
                        .connect(url)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .timeout(1000 * 30)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                        .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("accept-encoding","gzip, deflate, br")
                        .header("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                        .get();
                return doc;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                time++;
            }
        }
        return doc;
    }

    public static Document getJsoupDocPost(String url, Map<String,String> paramMap) {
        //三次试错
        final int MAX = 10;
        int time = 0;
        Document doc = null;
        while (time < MAX) {
            try {
                doc = Jsoup
                        .connect(url)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .timeout(1000 * 30)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                        .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("accept-encoding","gzip, deflate, br")
                        .header("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                        .data(paramMap)
                        .post();
                return doc;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                time++;
            }
        }
        return doc;
    }

}
