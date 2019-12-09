package com.jibug.cetty.sample.dao.mapper;

import com.jibug.cetty.sample.entity.domain.BrGoodsDetail;
import com.jibug.cetty.sample.entity.domain.BrGoodsDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrGoodsDetailMapper {
    long countByExample(BrGoodsDetailExample example);

    int deleteByExample(BrGoodsDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BrGoodsDetail record);

    int insertSelective(BrGoodsDetail record);

    List<BrGoodsDetail> selectByExample(BrGoodsDetailExample example);

    BrGoodsDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BrGoodsDetail record, @Param("example") BrGoodsDetailExample example);

    int updateByExample(@Param("record") BrGoodsDetail record, @Param("example") BrGoodsDetailExample example);

    int updateByPrimaryKeySelective(BrGoodsDetail record);

    int updateByPrimaryKey(BrGoodsDetail record);
}