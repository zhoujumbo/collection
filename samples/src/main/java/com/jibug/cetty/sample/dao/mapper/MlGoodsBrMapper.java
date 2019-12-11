package com.jibug.cetty.sample.dao.mapper;

import com.basic.support.commons.business.mybatis.query.ConditionQuery;
import com.jibug.cetty.sample.entity.domain.MlGoodsBr;
import com.jibug.cetty.sample.entity.domain.MlGoodsBrExample;
import com.jibug.cetty.sample.entity.domain.MlGoodsMx;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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

    void deleteOldData();
    List<MlGoodsBr> query(ConditionQuery query);
    int queryCnt(ConditionQuery query);
}