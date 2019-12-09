package com.jibug.cetty.sample.entity.domain;

import java.util.Date;

public class BrGoodsDetail {
    private Long id;

    private Date addTime;

    private String goodsId;

    private Byte osWarehouse;

    private Byte postFree;

    private Integer salesVolume;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
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
}