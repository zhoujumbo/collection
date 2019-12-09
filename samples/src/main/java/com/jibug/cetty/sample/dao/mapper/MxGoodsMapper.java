package com.jibug.cetty.sample.dao.mapper;

import com.jibug.cetty.sample.entity.domain.MxGoods;
import com.jibug.cetty.sample.entity.domain.MxGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MxGoodsMapper {
    long countByExample(MxGoodsExample example);

    int deleteByExample(MxGoodsExample example);

    int deleteByPrimaryKey(String goodsId);

    int insert(MxGoods record);

    int insertSelective(MxGoods record);

    List<MxGoods> selectByExample(MxGoodsExample example);

    MxGoods selectByPrimaryKey(String goodsId);

    int updateByExampleSelective(@Param("record") MxGoods record, @Param("example") MxGoodsExample example);

    int updateByExample(@Param("record") MxGoods record, @Param("example") MxGoodsExample example);

    int updateByPrimaryKeySelective(MxGoods record);

    int updateByPrimaryKey(MxGoods record);
}