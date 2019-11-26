package com.jibug.cetty.sample.dao;

import com.jibug.cetty.sample.entity.MlGoodsType;
import com.jibug.cetty.sample.repository.BasicRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MlGoodsTypeDao extends BasicRepository<MlGoodsType> {


//    @Modifying(clearAutomatically = true, flushAutomatically = true)
//    @Transactional
    @Query(value = "SELECT `id`, `series`, `entry`, `url1`, `url2`, `creat_time`,`area` " +
            " FROM `mercadolibre`.`ml_goods_type` " +
            " WHERE series=?1 AND entry=?2 AND area=?3", nativeQuery = true)
    List<MlGoodsType> queryOneByUnionKey(String series, String entry, String area);

    @Query(value = "SELECT `id`, `series`, `entry`, `url1`, `url2`, `creat_time`,`area` " +
            " FROM `mercadolibre`.`ml_goods_type` " +
            " WHERE area=?1", nativeQuery = true)
    List<MlGoodsType> queryListByArea(String area);


}
