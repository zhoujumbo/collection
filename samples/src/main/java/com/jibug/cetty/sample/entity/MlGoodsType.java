package com.jibug.cetty.sample.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ml_goods_type")
public class MlGoodsType implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "series",columnDefinition="varchar(50)")
    private String series;

    @Column(name = "entry",columnDefinition="varchar(60)")
    private String entry;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "creat_time",columnDefinition="timestamp")
    private Date creatTime;

    @Column(name = "area",columnDefinition="tinyint(4)")
    private Short area;

}
