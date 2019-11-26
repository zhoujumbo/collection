package com.durid;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

public class DuridPwd {


    @Test
    public void test01(){
        //加密
        try {
            String miwenofter = ConfigTools.encrypt("111111");
            System.out.println("加密后");
            System.out.println(miwenofter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02(){
        //解密
//        try {
////            String mingwen = ConfigTools.decrypt("111111111111111111111111111111111111");
//            System.out.println("解密后："+mingwen);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}


