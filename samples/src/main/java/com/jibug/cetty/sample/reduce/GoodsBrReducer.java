package com.jibug.cetty.sample.reduce;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Joiner;
import com.jibug.cetty.core.log.LogUtil;
import com.jibug.cetty.core.utils.FastJsonUtil;
import com.jibug.cetty.sample.entity.MlGoodsBr;
import com.jibug.cetty.sample.entity.MlGoodsType;
import com.jibug.cetty.sample.service.MlGoodsBrService;
import com.jibug.cetty.sample.service.MlGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * reduce聚合处理器
 *
 * @author heyingcai
 */
@Component
public class GoodsBrReducer extends MlReducer<MlGoodsBr,MlGoodsType> {

    private static Joiner joiner = Joiner.on("|");
    private static Map<String, String> oldBrMap = new ConcurrentHashMap<>(16);

    @Autowired
    private MlGoodsTypeService mlGoodsTypeService;
    @Autowired
    private MlGoodsBrService mlGoodsBrService;

    @Override
    protected void saveType(List<MlGoodsType> mlList) throws Exception {
        List<MlGoodsType> oldList = mlGoodsTypeService.queryListByArea("Br");
        List<MlGoodsType> saveRsult = new ArrayList<>();
        if (oldList == null) {
            try {
                mlGoodsTypeService.batchInseartOrUpdate(mlList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        oldList.forEach(old->{
            String key = this.getKey(old.getSeries(),old.getEntry());
            if(!oldBrMap.containsKey(key)){
                oldBrMap.put(key,key);
            }
        });
        mlList.forEach(ty->{
            if(!oldBrMap.containsKey(this.getKey(ty.getSeries(),ty.getEntry())) ){
                saveRsult.add(ty);
            }
        });
        if(saveRsult!=null && saveRsult.size()>0){
            mlGoodsTypeService.batchInseartOrUpdate(mlList);
        }

    }

    @Override
    protected void saveGoods(JSONArray arr){
        List<MlGoodsBr> mlList = FastJsonUtil.toBeanList(arr, MlGoodsBr.class);
        if(mlList==null || mlList.size()<=0){
            return;
        }
        try {
            mlGoodsBrService.saveAll(mlList);
        } catch (Exception e) {
            LogUtil.error("{}",e.getStackTrace());
            e.printStackTrace();
        }
    }


}
