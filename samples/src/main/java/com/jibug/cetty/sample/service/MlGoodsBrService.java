package com.jibug.cetty.sample.service;

import com.jibug.cetty.sample.entity.MlGoodsBr;
import com.jibug.cetty.sample.entity.MlGoodsMx;

import java.util.List;

public interface MlGoodsBrService {
    void save(MlGoodsBr mlGoodsBr);

    void saveAll(List<MlGoodsBr> entities) throws Exception;
}
