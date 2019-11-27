package com.jibug.cetty.sample.entity.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MlGoodsMx implements Serializable{
    private Long id;

    private BigDecimal price;

    private Date creatTime;

    private String goodsId;

    private Byte osWarehouse;

    private Byte postFree;

    private Integer salesVolume;

    private String series;

    private String title;

    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public Byte getOsWarehouse() {
        return osWarehouse;
    }

    public void setOsWarehouse(Byte osWarehouse) {
        this.osWarehouse = osWarehouse;
    }

    public Byte getPostFree() {
        return postFree;
    }

    public void setPostFree(Byte postFree) {
        this.postFree = postFree;
    }

    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series == null ? null : series.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}