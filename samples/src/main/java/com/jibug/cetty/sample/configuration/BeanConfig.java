package com.jibug.cetty.sample.configuration;


import com.jibug.cetty.sample.container.BrSourceDataContainer;
import com.jibug.cetty.sample.container.MxSourceDataContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {


    @Bean
    public BrSourceDataContainer brContainer(){
        return new BrSourceDataContainer();
    }

    @Bean
    public MxSourceDataContainer mxContainer(){
        return new MxSourceDataContainer();
    }



}
