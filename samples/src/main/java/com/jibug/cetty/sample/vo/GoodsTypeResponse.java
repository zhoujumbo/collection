package com.jibug.cetty.sample.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName GoodsTypeResponse
 */
@Data
public class GoodsTypeResponse implements Serializable{

    private Long id;
    private String series;
    private String entry;
    private String url1;
    private String url2;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creatTime;
    private String area;

}
