package com.jibug.cetty.sample.dao.mapper;

import com.jibug.cetty.sample.entity.domain.MlGoodsBr;
import com.jibug.cetty.sample.entity.domain.MlGoodsBrExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MlGoodsBrMapper {
    long countByExample(MlGoodsBrExample example);

    int deleteByExample(MlGoodsBrExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MlGoodsBr record);

    int insertSelective(MlGoodsBr record);

    int insertBatch(List<MlGoodsBr> record);

    List<MlGoodsBr> selectByExample(MlGoodsBrExample example);

    MlGoodsBr selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MlGoodsBr record, @Param("example") MlGoodsBrExample example);

    int updateByExample(@Param("record") MlGoodsBr record, @Param("example") MlGoodsBrExample example);

    int updateByPrimaryKeySelective(MlGoodsBr record);

    int updateByPrimaryKey(MlGoodsBr record);
}