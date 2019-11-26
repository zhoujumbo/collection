package com.jibug.cetty.sample.dao;

import com.jibug.cetty.sample.entity.TableManage;
import com.jibug.cetty.sample.repository.BasicRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableManageDao extends BasicRepository<TableManage> {


    @Query(value = "CREATE TABLE  ?1 " +
            "SELECT  a3.goods_id,a3.title ,a3.series, a3.actual_price " +
            ",a3.min_time, a3.min ,date_add(a3.min_time, interval (a3.total-1) day) as max_time ,a3.max " +
            ",a3.sub FROM  ( " +
            "       select a2.goods_id,a2.title ,a2.series, a2.actual_price, count(1) as total ,a2.creat_time as min_time " +
            "           ,MIN(a2.sales_volume+0) as min,MAX(a2.sales_volume+0) as max,(MAX(a2.sales_volume+0) - MIN(a2.sales_volume+0)) as sub " +
            "       from ( " +
            "           select * from ( " +
            "               SELECT `goods_id`, `title`, `series`, actual_price+'' as actual_price, max+'' as 'sales_volume' , date_format(max_time ,'%Y-%m-%d' ) creat_time " +
            "               FROM ?2  " +
            "               UNION ALL " +
            "               SELECT goods_id,title,series,actual_price,sales_volume,date_format(creat_time ,'%Y-%m-%d' ) creat_time " +
            "               FROM ?3 " +
            "       ) as temp " +
            "               group by goods_id,creat_time " +
            "       ) a2 group by a2.goods_id,a2.title having count(1)>0 " +
            " ) as a3   " +
            " ORDER BY a3.series ASC,a3.sub DESC", nativeQuery = true)
    void creatTableByName(String cTableName, String sTableName, String resourceTable);



    @Query(value ="SELECT id,origin,status,`length`,creat_time,CONCAT(`table_name`,'') table_name from  ml_table_record WHERE origin=?1 and status<>1 ORDER BY creat_time DESC LIMIT 0,1", nativeQuery = true)
    List<TableManage> getTopOne(String origin);
    // creat
    @Query(value ="CREATE TABLE ?1", nativeQuery = true)
    void creatByName(String origin);

    // creat
    @Query(value ="create table ?1 like ?2;", nativeQuery = true)
    void creatTableByTable(String name,String origin);

    // 判断
    @Query(value ="select TABLE_NAME from INFORMATION_SCHEMA.TABLES WHERE  TABLE_SCHEMA='mercadolibre' AND TABLE_NAME=?1", nativeQuery = true)
    void  tableIsExists(String origin);
}

