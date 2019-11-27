package com.jibug.cetty.sample.dao.mapper;

import com.jibug.cetty.sample.entity.domain.MlGoodsMx;
import com.jibug.cetty.sample.entity.domain.MlGoodsMxExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface MlGoodsMxMapper {
    long countByExample(MlGoodsMxExample example);

    int deleteByExample(MlGoodsMxExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MlGoodsMx record);

    int insertSelective(MlGoodsMx record);

    int insertBatch(List<MlGoodsMx> record);

    List<MlGoodsMx> selectByExample(MlGoodsMxExample example);

    MlGoodsMx selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MlGoodsMx record, @Param("example") MlGoodsMxExample example);

    int updateByExample(@Param("record") MlGoodsMx record, @Param("example") MlGoodsMxExample example);

    int updateByPrimaryKeySelective(MlGoodsMx record);

    int updateByPrimaryKey(MlGoodsMx record);
}