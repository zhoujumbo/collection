package com.jibug.cetty.sample.dao.mapper;

import com.jibug.cetty.sample.entity.domain.MlGoodsType;
import com.jibug.cetty.sample.entity.domain.MlGoodsTypeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MlGoodsTypeMapper {
    long countByExample(MlGoodsTypeExample example);

    int deleteByExample(MlGoodsTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MlGoodsType record);

    int insertSelective(MlGoodsType record);

    int insertBatch(List<MlGoodsType> record);

    List<MlGoodsType> selectByExample(MlGoodsTypeExample example);

    MlGoodsType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MlGoodsType record, @Param("example") MlGoodsTypeExample example);

    int updateByExample(@Param("record") MlGoodsType record, @Param("example") MlGoodsTypeExample example);

    int updateByPrimaryKeySelective(MlGoodsType record);

    int updateByPrimaryKey(MlGoodsType record);
}