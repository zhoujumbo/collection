package com.jibug.cetty.sample.dao;

import com.jibug.cetty.sample.entity.MlGoodsMxPo;
import com.jibug.cetty.sample.repository.BasicRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MlGoodsMxDao  extends BasicRepository<MlGoodsMxPo> {

    @Query(value = "SELECT * FROM ml_goods_mx", nativeQuery = true)
    List<MlGoodsMxPo> queryCusAll();
}
