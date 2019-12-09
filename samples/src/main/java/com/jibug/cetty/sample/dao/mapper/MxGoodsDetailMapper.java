package com.jibug.cetty.sample.dao.mapper;

import com.jibug.cetty.sample.entity.domain.MxGoodsDetail;
import com.jibug.cetty.sample.entity.domain.MxGoodsDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MxGoodsDetailMapper {
    long countByExample(MxGoodsDetailExample example);

    int deleteByExample(MxGoodsDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MxGoodsDetail record);

    int insertSelective(MxGoodsDetail record);

    List<MxGoodsDetail> selectByExample(MxGoodsDetailExample example);

    MxGoodsDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MxGoodsDetail record, @Param("example") MxGoodsDetailExample example);

    int updateByExample(@Param("record") MxGoodsDetail record, @Param("example") MxGoodsDetailExample example);

    int updateByPrimaryKeySelective(MxGoodsDetail record);

    int updateByPrimaryKey(MxGoodsDetail record);
}