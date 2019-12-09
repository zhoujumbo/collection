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
@Table(name = "mx_goods_detail")
public class MxGoodsDetailPo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goods_id",columnDefinition="varchar(20)")
    private String goodsId;

    @Column(name = "post_free",columnDefinition="tinyint(4)")
    private Short postFree;

    @Column(name = "os_warehouse",columnDefinition="tinyint(4)")
    private Short osWarehouse;

    @Column(name = "sales_volume", columnDefinition="int(11)")
    private String salesVolume;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "add_time",columnDefinition="timestamp")
    private Date addTime;





}
