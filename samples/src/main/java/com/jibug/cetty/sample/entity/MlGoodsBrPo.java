package com.jibug.cetty.sample.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "ml_goods_br")
public class MlGoodsBrPo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goods_id",columnDefinition="varchar(20)")
    private String goodsId;

    @Column(name = "title",columnDefinition="varchar(200)")
    private String title;

    @Column(name = "series",columnDefinition="varchar(50)")
    private String series;

    @Column(name = "price",columnDefinition="decimal(10,2)")
    private BigDecimal actualPrice;

    @Column(name = "url",columnDefinition="varchar(999)")
    private String url1;

    @Column(name = "post_free",columnDefinition="tinyint(4)")
    private Short postFree;

    @Column(name = "os_warehouse",columnDefinition="tinyint(4)")
    private Short osWarehouse;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "creat_time",columnDefinition="timestamp")
    private Date creatTime;


    @Column(name = "sales_volume", columnDefinition="int(11)")
    private String salesVolume;


    public MlGoodsBrPo setId(Long id) {
        this.id = id;
        return this;
    }

    public MlGoodsBrPo setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
        return this;
    }

    public MlGoodsBrPo setTitle(String title) {
        this.title = title == null ? null : title.trim();
        return this;
    }

    public MlGoodsBrPo setSeries(String series) {
        this.series = series == null ? null : series.trim();
        return this;
    }

    public MlGoodsBrPo setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
        return this;
    }

    public MlGoodsBrPo setUrl1(String url1) {
        this.url1 = url1 == null ? null : url1.trim();
        return this;
    }

    public MlGoodsBrPo setPostFree(Short postFree) {
        this.postFree = postFree;
        return this;
    }

    public MlGoodsBrPo setOsWarehouse(Short osWarehouse) {
        this.osWarehouse = osWarehouse;
        return this;
    }

    public MlGoodsBrPo setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
        return this;
    }

    public MlGoodsBrPo setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume == null ? null : salesVolume.trim();
        return this;
    }
}
