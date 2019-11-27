package com.jibug.cetty.sample.dao;

import com.jibug.cetty.sample.entity.MlGoodsBrPo;
import com.jibug.cetty.sample.repository.BasicRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MlGoodsBrDao extends BasicRepository<MlGoodsBrPo> {

    @Query(value = "SELECT * FROM ml_goods_br", nativeQuery = true)
    List<MlGoodsBrPo> queryCusAll();
}
