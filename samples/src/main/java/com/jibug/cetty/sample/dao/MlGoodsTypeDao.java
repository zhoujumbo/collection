package com.jibug.cetty.sample.dao;

import com.jibug.cetty.sample.entity.MlGoodsTypePo;
import com.jibug.cetty.sample.repository.BasicRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MlGoodsTypeDao extends BasicRepository<MlGoodsTypePo> {


//    @Modifying(clearAutomatically = true, flushAutomatically = true)
//    @Transactional
    @Query(value = "SELECT `id`, `series`, `entry`, `url1`, `url2`, `creat_time`,`area` " +
            " FROM `mercadolibre`.`ml_goods_type` " +
            " WHERE series=?1 AND entry=?2 AND area=?3", nativeQuery = true)
    List<MlGoodsTypePo> queryOneByUnionKey(String series, String entry, Short area);

    @Query(value = "SELECT `id`, `series`, `entry`, `url1`, `url2`, `creat_time`,`area` " +
            " FROM `mercadolibre`.`ml_goods_type` " +
            " WHERE area=?1", nativeQuery = true)
    List<MlGoodsTypePo> queryListByArea(Short area);


}
