package com.jibug.cetty.sample.service.impl;

import com.jibug.cetty.sample.dao.MlGoodsTypeDao;
import com.jibug.cetty.sample.entity.MlGoodsType;
import com.jibug.cetty.sample.service.MlGoodsTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class MlGoodsTypeServiceImpl implements MlGoodsTypeService {

    @Autowired
    private MlGoodsTypeDao mlGoodsTypeDao;


    /**
     * save
     * @param
     */
    @Override
    public void save(MlGoodsType mlGoodsType){
        mlGoodsTypeDao.save(mlGoodsType);
    }


    @Override
    @Transactional
    public void batchInseartOrUpdate(@NotNull List<MlGoodsType> entities) throws Exception{

        entities.forEach(entity->{
            List<MlGoodsType> mlGoodsMxList =  mlGoodsTypeDao.queryOneByUnionKey(entity.getSeries(),entity.getEntry(),entity.getArea());
            if(mlGoodsMxList==null || mlGoodsMxList.size()<=0){
                mlGoodsTypeDao.saveAndFlush(entity);
            }else{
                mlGoodsMxList.forEach(mltype->{
                    MlGoodsType mlGoodsType = mltype;
                    BeanUtils.copyProperties(entity, mltype);
                    mltype.setId(mlGoodsType.getId());
                    mlGoodsTypeDao.saveAndFlush(mltype);
                });
            }
        });
    }

    @Override
    public List<MlGoodsType> queryOneByUnionKey(String series, String entry, String area){
        return mlGoodsTypeDao.queryOneByUnionKey(series, entry, area);
    }

    @Override
    public List<MlGoodsType> queryListByArea(String area){
        return mlGoodsTypeDao.queryListByArea(area);
    }


}
