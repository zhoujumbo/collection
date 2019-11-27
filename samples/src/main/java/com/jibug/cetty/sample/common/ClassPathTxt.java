package com.jibug.cetty.sample.common;

import com.basic.support.commons.business.logger.LogUtil;
import com.jibug.cetty.core.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClassPathTxt {

    @Value("${config.file}")
    private String config;

    public void write2Txt(String content) throws IOException {
        if(!FileUtil.isExistsPath(config)){
            LogUtil.error("配置文件不存在");
            System.out.println("配置文件不存在");
            return;
        }
        Path path = Paths.get(config);
        try(BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
            writer.newLine();
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write2TxtDelOld(List<String> lines) throws IOException {
        if(StringUtils.isEmpty(config)){
            LogUtil.error("参数获取失败  config");
            return;
        }
        Path path = Paths.get(config);
        Files.write(path,lines);
    }

    public Map<String, String> getConfigMap(){
        // 读取文件内容
        Map<String, String> result = new HashMap<>(16);
        try {
            if(!FileUtil.isExistsPath(config)){
                LogUtil.error("配置文件不存在");
                System.out.println("配置文件不存在");
                return null;
            }
            Path path = Paths.get(config);
            Files
                    .lines(path, Charset.defaultCharset())
                    .map(line -> {
                        Map<String, String> map = new HashMap<>(16);
                        if(StringUtils.isNotEmpty(line)){
                            String[] arr = line.split(":");
                            if(arr.length>1){
                                map.put(arr[0],arr[1]);
                            }
                        }
                        return map;
                    })
                    .forEach(m->{
                        result.putAll(m);
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }




    public boolean isInvalid(){
        return true;
    }
//        Map<String, String> stausMap = this.getConfigMap();
//        String status = stausMap.get("status");
//        if(status.equals("true")){
//            return true;
//        }
//        return false;
//    }


}
