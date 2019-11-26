package com.jibug.cetty.sample.service;

import com.jibug.cetty.sample.entity.MlGoodsType;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MlGoodsTypeService {
    void save(MlGoodsType mlGoodsType);

    void batchInseartOrUpdate(@NotNull List<MlGoodsType> entities) throws Exception;

    List<MlGoodsType> queryOneByUnionKey(String series, String entry, String area);

    List<MlGoodsType> queryListByArea(String area);
}
