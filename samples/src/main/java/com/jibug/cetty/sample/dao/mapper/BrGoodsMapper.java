package com.jibug.cetty.sample.dao.mapper;

import com.jibug.cetty.sample.entity.domain.BrGoods;
import com.jibug.cetty.sample.entity.domain.BrGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrGoodsMapper {
    long countByExample(BrGoodsExample example);

    int deleteByExample(BrGoodsExample example);

    int deleteByPrimaryKey(String goodsId);

    int insert(BrGoods record);

    int insertSelective(BrGoods record);

    List<BrGoods> selectByExample(BrGoodsExample example);

    BrGoods selectByPrimaryKey(String goodsId);

    int updateByExampleSelective(@Param("record") BrGoods record, @Param("example") BrGoodsExample example);

    int updateByExample(@Param("record") BrGoods record, @Param("example") BrGoodsExample example);

    int updateByPrimaryKeySelective(BrGoods record);

    int updateByPrimaryKey(BrGoods record);
}