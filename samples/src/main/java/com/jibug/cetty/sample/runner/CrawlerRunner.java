package com.jibug.cetty.sample.runner;

import com.jibug.cetty.sample.common.ClassPathTxt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 启动任务
 * @author heyingcai
 * @date 2019-07-02 17:53
 */
@Component
public class CrawlerRunner implements CommandLineRunner {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    private ClassPathTxt classPathTxt;

    @Value("${user.key}")
    private String longKey;
    @Value("${user.deftoken}")
    private String defToken;

    @Override
    public void run(String... args) throws Exception {
//        List<String> writeContent = new ArrayList<>();
//        Map<String, String> configMap = classPathTxt.getConfigMap();
//        String mac = "";
//        try {
//            mac = MacUtil.getMACAddress();
//        } catch (Exception e) {
//            return;
//        }
//
//        String key = AESUtil.decode(longKey);
//        String defTokenDec = AESUtil.decode(defToken,key);
//        String unionToken = AESUtil.encode(defTokenDec+"_"+mac, key);
//        if(!configMap.containsKey("token")){
//            try {
//                writeContent.add("token:aaaaaaaaaaaaaa\n");
//                writeContent.add("status:false\n");
//                writeContent.add("msg:token获取失败\n");
//                classPathTxt.write2TxtDelOld(writeContent);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }else{
//            String token = configMap.get("token");
//            if(token.equals(defToken)){
//                String newToken = AESUtil.encode(defTokenDec+"_"+mac, key);
//                try {
//                    writeContent.add("token:"+newToken+"\n");
//                    writeContent.add("status:true\n");
//                    writeContent.add("msg:初始化成功\n");
//                    classPathTxt.write2TxtDelOld(writeContent);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }else if(token.equals(unionToken)){
//                try {
//                    writeContent.add("token:"+token+"\n");
//                    writeContent.add("status:true\n");
//                    writeContent.add("msg:状态正常 success\n");
//                    classPathTxt.write2TxtDelOld(writeContent);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }else{
//                try {
//                    writeContent.add("token:"+token+"\n");
//                    writeContent.add("status:false\n");
//                    writeContent.add("msg:状态异常 false\n");
//                    classPathTxt.write2TxtDelOld(writeContent);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
    }


}
