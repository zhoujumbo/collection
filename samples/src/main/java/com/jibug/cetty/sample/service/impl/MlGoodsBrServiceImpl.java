package com.jibug.cetty.sample.service.impl;

import com.jibug.cetty.sample.dao.MlGoodsBrDao;
import com.jibug.cetty.sample.dao.MlGoodsMxDao;
import com.jibug.cetty.sample.entity.MlGoodsBr;
import com.jibug.cetty.sample.entity.MlGoodsMx;
import com.jibug.cetty.sample.service.MlGoodsBrService;
import com.jibug.cetty.sample.service.MlGoodsMxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MlGoodsBrServiceImpl implements MlGoodsBrService {

    @Autowired
    private MlGoodsBrDao mlGoodsBrDao;

    /**
     * save
     * @param mlGoodsBr
     */
    @Override
    public void save(MlGoodsBr mlGoodsBr){
        synchronized (this){
            mlGoodsBrDao.save(mlGoodsBr);
        }
    }


    @Override
    public void saveAll(List<MlGoodsBr> entities) throws Exception{
        mlGoodsBrDao.saveAll(entities);
    }

}
