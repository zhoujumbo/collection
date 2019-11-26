package com.jibug.cetty.sample.service;

import com.jibug.cetty.sample.entity.MlGoodsMx;

import java.util.List;

public interface MlGoodsMxService {
    void save(MlGoodsMx mlGoodsMx);

    void saveAll(List<MlGoodsMx> entities) throws Exception;

    void deleteAll();


    void deleteBatch(List<MlGoodsMx> entities);

}
