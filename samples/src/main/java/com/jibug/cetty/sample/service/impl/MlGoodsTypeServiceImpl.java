package com.jibug.cetty.sample.service.impl;

import com.jibug.cetty.sample.dao.MlGoodsTypeDao;
import com.jibug.cetty.sample.dao.mapper.MlGoodsTypeMapper;
import com.jibug.cetty.sample.entity.MlGoodsTypePo;
import com.jibug.cetty.sample.entity.domain.MlGoodsType;
import com.jibug.cetty.sample.service.MlGoodsTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class MlGoodsTypeServiceImpl implements MlGoodsTypeService {

    @Autowired
    private MlGoodsTypeDao mlGoodsTypeDao;
    @Autowired
    private MlGoodsTypeMapper mlGoodsTypeMapper;

    /**
     * save
     * @param
     */
    @Override
    public void save(MlGoodsTypePo mlGoodsTypePo){
        mlGoodsTypeDao.save(mlGoodsTypePo);
    }


    @Override
    @Transactional
    public void batchInseartOrUpdate(@NotNull List<MlGoodsTypePo> entities) throws Exception{

        entities.forEach(entity->{
            List<MlGoodsTypePo> mlGoodsMxList =  mlGoodsTypeDao.queryOneByUnionKey(entity.getSeries(),entity.getEntry(),entity.getArea());
            if(mlGoodsMxList==null || mlGoodsMxList.size()<=0){
                mlGoodsTypeDao.saveAndFlush(entity);
            }else{
                mlGoodsMxList.forEach(mltype->{
                    MlGoodsTypePo mlGoodsTypePo = mltype;
                    BeanUtils.copyProperties(entity, mltype);
                    mltype.setId(mlGoodsTypePo.getId());
                    mlGoodsTypeDao.saveAndFlush(mltype);
                });
            }
        });
    }

    @Override
    public List<MlGoodsTypePo> queryOneByUnionKey(String series, String entry, Short area){
        return mlGoodsTypeDao.queryOneByUnionKey(series, entry, area);
    }

    @Override
    public List<MlGoodsTypePo> queryListByArea(Short area){
        return mlGoodsTypeDao.queryListByArea(area);
    }


    /**
     * insert
     * @param mlGoodsType
     */
    @Override
    public void insert(MlGoodsType mlGoodsType){
        Optional.ofNullable(mlGoodsType)
                .ifPresent(mlGoodsTypeMapper::insert);
    }

    /**
     *
     * insertBatch
     * @param mlGoodsTypeList
     */
    @Override
    public void insertBatch(List<MlGoodsType> mlGoodsTypeList){
        if(!mlGoodsTypeList.isEmpty()){
            mlGoodsTypeMapper.insertBatch(mlGoodsTypeList);
        }
    }

}
