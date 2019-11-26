package com.jibug.cetty.sample.common.properties.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sample.conf")
@Setter
@Getter
public class SampleProperties {

}
