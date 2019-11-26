package com.jibug.cetty.sample.service.impl;

import com.jibug.cetty.sample.dao.MlGoodsMxDao;
import com.jibug.cetty.sample.entity.MlGoodsMx;
import com.jibug.cetty.sample.service.MlGoodsMxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MlGoodsMxServiceImpl implements MlGoodsMxService {

    @Autowired
    private MlGoodsMxDao mlGoodsMxDao;

    /**
     * save
     * @param mlGoodsMx
     */
    @Override
    public void save(MlGoodsMx mlGoodsMx){
        mlGoodsMxDao.save(mlGoodsMx);
    }

    @Override
    public void saveAll(List<MlGoodsMx> entities) throws Exception{
        mlGoodsMxDao.saveAll(entities);
    }

    @Override
    public void deleteAll(){
        mlGoodsMxDao.deleteAll();
    }


    @Override
    public void deleteBatch(List<MlGoodsMx> entities){
        mlGoodsMxDao.deleteAll(entities);
    }

}
