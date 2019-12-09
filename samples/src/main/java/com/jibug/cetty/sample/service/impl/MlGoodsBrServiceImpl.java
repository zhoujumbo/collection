package com.jibug.cetty.sample.service.impl;

import com.jibug.cetty.sample.dao.MlGoodsBrDao;
import com.jibug.cetty.sample.dao.mapper.MlGoodsBrMapper;
import com.jibug.cetty.sample.entity.MlGoodsBrPo;
import com.jibug.cetty.sample.entity.domain.MlGoodsBr;
import com.jibug.cetty.sample.service.MlGoodsBrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MlGoodsBrServiceImpl implements MlGoodsBrService {

    @Autowired
    private MlGoodsBrDao mlGoodsBrDao;

    @Autowired
    private MlGoodsBrMapper mlGoodsBrMapper;

    /**
     * save
     * @param mlGoodsBrPo
     */
    @Override
    public void save(MlGoodsBrPo mlGoodsBrPo){
        synchronized (this){
            mlGoodsBrDao.save(mlGoodsBrPo);
        }
    }


    @Override
    public void saveAll(List<MlGoodsBrPo> entities) throws Exception{
        mlGoodsBrDao.saveAll(entities);
    }

    /**
     * insertBatch
     * @param mlGoodsBrList
     */
    @Override
    public void insertBatch(List<MlGoodsBr> mlGoodsBrList){
        if(!mlGoodsBrList.isEmpty()){
            mlGoodsBrMapper.insertBatch(mlGoodsBrList);
        }
    }


    @Override
    public void deleteOldData(){
        mlGoodsBrMapper.deleteOldData();
    }
}
