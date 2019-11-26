package com.jibug.cetty.sample.common.properties;

import com.jibug.cetty.sample.common.properties.config.SampleProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(SampleProperties.class)
public class PropertiesConfig {
}
