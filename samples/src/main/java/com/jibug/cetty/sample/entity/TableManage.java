package com.jibug.cetty.sample.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ml_table_record")
public class TableManage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "origin",columnDefinition="varchar(64)")
    private String origin;

    @Column(name = "table_name",columnDefinition="varchar(255)")
    private String table_name;

    @Column(name = "length",columnDefinition="int(11)")
    private String length;

    @Column(name = "creat_time",columnDefinition="DATE")
    private String creat_time;

    @Column(name = "status",columnDefinition="tinyint(4)")
    private short status;



}
