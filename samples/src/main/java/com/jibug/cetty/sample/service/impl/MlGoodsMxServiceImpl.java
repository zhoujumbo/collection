package com.jibug.cetty.sample.service.impl;

import com.basic.support.commons.business.crud.pojo.Grid;
import com.basic.support.commons.business.mybatis.query.ConditionQuery;
import com.basic.support.commons.business.result.ResponseCode;
import com.jibug.cetty.sample.dao.MlGoodsMxDao;
import com.jibug.cetty.sample.dao.mapper.MlGoodsMxMapper;
import com.jibug.cetty.sample.entity.MlGoodsMxPo;
import com.jibug.cetty.sample.entity.domain.MlGoodsMx;
import com.jibug.cetty.sample.entity.query.GoodsMxQuery;
import com.jibug.cetty.sample.service.MlGoodsMxService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MlGoodsMxServiceImpl implements MlGoodsMxService {

    @Autowired
    private MlGoodsMxDao mlGoodsMxDao;
    @Autowired
    private MlGoodsMxMapper mlGoodsMxMapper;


    @Override
    public Grid gridData(GoodsMxQuery goodsMxQuery) {
        ConditionQuery query = goodsMxQuery.buildConditionQuery();

        int count = this.mlGoodsMxMapper.queryCnt(query);
        List<MlGoodsMx> list = new ArrayList<>();
        if (count > 0) {
            query.getParamMap().put("firstResult", goodsMxQuery.getFirstResult());
            query.getParamMap().put("rp", goodsMxQuery.getRp());
            list = this.mlGoodsMxMapper.query(query);
        }
        if(!CollectionUtils.isEmpty(list)){
            goodsMxQuery.convert(ResponseCode.SUCCESS.value(),"数据加载完成",list,count);
        }else{
            goodsMxQuery.convert(ResponseCode.ERROR.value(),"暂无数据");
        }
        return goodsMxQuery;
    }




    /**
     * save
     * @param mlGoodsMxPo
     */
    @Override
    public void save(MlGoodsMxPo mlGoodsMxPo){
        mlGoodsMxDao.save(mlGoodsMxPo);
    }

    @Override
    public void saveAll(List<MlGoodsMxPo> entities) throws Exception{
        mlGoodsMxDao.saveAll(entities);
    }

    @Override
    public void deleteAll(){
        mlGoodsMxDao.deleteAll();
    }


    @Override
    public void deleteBatch(List<MlGoodsMxPo> entities){
        mlGoodsMxDao.deleteAll(entities);
    }

    /**
     * insertBatch
     * @param mlGoodsMxList
     */
    @Override
    public void insertBatch(List<MlGoodsMx> mlGoodsMxList){
        if(!mlGoodsMxList.isEmpty()){
            mlGoodsMxMapper.insertBatch(mlGoodsMxList);
        }
    }

    @Override
    public void deleteOldData(){
        mlGoodsMxMapper.deleteOldData();
    }

}
