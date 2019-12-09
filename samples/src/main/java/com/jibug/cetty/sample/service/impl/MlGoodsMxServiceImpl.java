package com.jibug.cetty.sample.service.impl;

import com.jibug.cetty.sample.dao.MlGoodsMxDao;
import com.jibug.cetty.sample.dao.mapper.MlGoodsMxMapper;
import com.jibug.cetty.sample.entity.MlGoodsMxPo;
import com.jibug.cetty.sample.entity.domain.MlGoodsMx;
import com.jibug.cetty.sample.service.MlGoodsMxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MlGoodsMxServiceImpl implements MlGoodsMxService {

    @Autowired
    private MlGoodsMxDao mlGoodsMxDao;
    @Autowired
    private MlGoodsMxMapper mlGoodsMxMapper;

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
