package com.jibug.cetty.sample.dao;

import com.jibug.cetty.sample.entity.MlGoodsMx;
import com.jibug.cetty.sample.repository.BasicRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MlGoodsMxDao  extends BasicRepository<MlGoodsMx> {

    @Query(value = "SELECT * FROM ml_goods_mx", nativeQuery = true)
    List<MlGoodsMx> queryCusAll();
}
