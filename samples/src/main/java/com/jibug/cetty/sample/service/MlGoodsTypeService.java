package com.jibug.cetty.sample.service;

import com.jibug.cetty.sample.entity.MlGoodsTypePo;
import com.jibug.cetty.sample.entity.domain.MlGoodsType;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MlGoodsTypeService {
    void save(MlGoodsTypePo mlGoodsTypePo);

    void batchInseartOrUpdate(@NotNull List<MlGoodsTypePo> entities) throws Exception;

    List<MlGoodsTypePo> queryOneByUnionKey(String series, String entry, Short area);

    List<MlGoodsTypePo> queryListByArea(Short area);

    void insert(MlGoodsType mlGoodsType);

    void insertBatch(List<MlGoodsType> mlGoodsTypeList);
}
