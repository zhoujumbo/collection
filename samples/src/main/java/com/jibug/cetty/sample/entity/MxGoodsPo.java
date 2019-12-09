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
@Table(name = "mx_goods",
        uniqueConstraints={@UniqueConstraint(columnNames={"goods_id"})},
        indexes = {@Index(columnList = "goods_id")})
public class MxGoodsPo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "goods_id",columnDefinition="varchar(20)", nullable = false)
    private String goodsId;

    @Column(name = "title",columnDefinition="varchar(200)")
    private String title;

    @Column(name = "series",columnDefinition="varchar(50)")
    private String series;

    @Column(name = "price",columnDefinition="decimal(10,2)")
    private BigDecimal actualPrice;

    @Column(name = "url",columnDefinition="varchar(999)")
    private String url1;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "creat_time",columnDefinition="timestamp")
    private Date creatTime;






}
