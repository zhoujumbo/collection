package com.jibug.cetty.sample.service;

import com.basic.support.commons.business.crud.pojo.Grid;
import com.jibug.cetty.sample.entity.MlGoodsMxPo;
import com.jibug.cetty.sample.entity.domain.MlGoodsMx;
import com.jibug.cetty.sample.entity.query.GoodsMxQuery;

import java.util.List;

public interface MlGoodsMxService {
    Grid gridData(GoodsMxQuery goodsMxQuery);

    void save(MlGoodsMxPo mlGoodsMxPo);

    void saveAll(List<MlGoodsMxPo> entities) throws Exception;

    void deleteAll();


    void deleteBatch(List<MlGoodsMxPo> entities);

    void insertBatch(List<MlGoodsMx> mlGoodsMxList);

    void deleteOldData();
}
