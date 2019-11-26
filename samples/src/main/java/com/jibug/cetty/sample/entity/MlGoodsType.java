package com.jibug.cetty.sample.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Data
@Entity
@Table(name = "ml_goods_type")
public class MlGoodsType implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "series")
    private String series;

    @Column(name = "entry")
    private String entry;

    @Column(name = "url1")
    private String url1;

    @Column(name = "url2")
    private String url2;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "creat_time")
    private Date creatTime;

    @Column(name = "area")
    private String area;

    public MlGoodsType setId(Long id) {
        this.id = id;
        return this;
    }

    public MlGoodsType setSeries(String series) {
        this.series = series == null ? null : series.trim();
        return this;
    }

    public MlGoodsType setEntry(String entry) {
        this.entry = entry == null ? null : entry.trim();
        return this;
    }

    public MlGoodsType setUrl1(String url1) {
        this.url1 = url1 == null ? null : url1.trim();
        return this;
    }

    public MlGoodsType setUrl2(String url2) {
        this.url2 = url2 == null ? null : url2.trim();
        return this;
    }

    public MlGoodsType setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
        return this;
    }

    public MlGoodsType setArea(String area) {
        this.area = area == null ? null : area.trim();
        return this;
    }
}
