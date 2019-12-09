package com.jibug.cetty.sample.service;

import com.jibug.cetty.sample.entity.MlGoodsBrPo;
import com.jibug.cetty.sample.entity.domain.MlGoodsBr;

import java.util.List;

public interface MlGoodsBrService {
    void save(MlGoodsBrPo mlGoodsBrPo);

    void saveAll(List<MlGoodsBrPo> entities) throws Exception;

    void insertBatch(List<MlGoodsBr> mlGoodsBrList);

    void deleteOldData();
}
