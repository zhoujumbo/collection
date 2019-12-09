package com.jibug.cetty.sample.service;

import com.jibug.cetty.sample.entity.MlGoodsMxPo;
import com.jibug.cetty.sample.entity.domain.MlGoodsMx;

import java.util.List;

public interface MlGoodsMxService {
    void save(MlGoodsMxPo mlGoodsMxPo);

    void saveAll(List<MlGoodsMxPo> entities) throws Exception;

    void deleteAll();


    void deleteBatch(List<MlGoodsMxPo> entities);

    void insertBatch(List<MlGoodsMx> mlGoodsMxList);

    void deleteOldData();
}
