package com.jibug.cetty.sample.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ml_goods_mx")
public class MlGoodsMx implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goods_id")
    private String goodsId;

    @Column(name = "title")
    private String title;

    @Column(name = "series")
    private String series;

    @Column(name = "specification")
    private String specification;

    @Column(name = "color")
    private String color;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "currency")
    private String currency;

    @Column(name = "price")
    private String price;

    @Column(name = "discount")
    private String discount;

    @Column(name = "actual_price")
    private String actualPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "url1")
    private String url1;

    @Column(name = "url2")
    private String url2;

    @Column(name = "url3")
    private String url3;

    @Column(name = "post_free",columnDefinition="varchar(4)")
    private String postFree;

    @Column(name = "os_warehouse",columnDefinition="varchar(4)")
    private String osWarehouse;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "creat_time")
    private Date creatTime;


    @Column(name = "sales_volume")
    private String salesVolume;

    public MlGoodsMx setId(Long id) {
        this.id = id;
        return this;
    }

    public MlGoodsMx setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
        return this;
    }

    public MlGoodsMx setTitle(String title) {
        this.title = title == null ? null : title.trim();
        return this;
    }

    public MlGoodsMx setSeries(String series) {
        this.series = series == null ? null : series.trim();
        return this;
    }

    public MlGoodsMx setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
        return this;
    }

    public MlGoodsMx setColor(String color) {
        this.color = color == null ? null : color.trim();
        return this;
    }

    public MlGoodsMx setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
        return this;
    }

    public MlGoodsMx setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
        return this;
    }

    public MlGoodsMx setPrice(String price) {
        this.price = price == null ? null : price.trim();
        return this;
    }

    public MlGoodsMx setDiscount(String discount) {
        this.discount = discount == null ? null : discount.trim();
        return this;
    }

    public MlGoodsMx setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice == null ? null : actualPrice.trim();
        return this;
    }

    public MlGoodsMx setDescription(String description) {
        this.description = description == null ? null : description.trim();
        return this;
    }

    public MlGoodsMx setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public MlGoodsMx setUrl1(String url1) {
        this.url1 = url1 == null ? null : url1.trim();
        return this;
    }

    public MlGoodsMx setUrl2(String url2) {
        this.url2 = url2 == null ? null : url2.trim();
        return this;
    }

    public MlGoodsMx setUrl3(String url3) {
        this.url3 = url3 == null ? null : url3.trim();
        return this;
    }

    public MlGoodsMx setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
        return this;
    }

    public MlGoodsMx setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume == null ? null : salesVolume.trim();
        return this;
    }

    public MlGoodsMx setPostFree(String postFree) {
        this.postFree = postFree == null ? null : postFree.trim();
        return this;
    }

    public MlGoodsMx setOsWarehouse(String osWarehouse) {
        this.osWarehouse = osWarehouse == null ? null : osWarehouse.trim();
        return this;
    }
}
