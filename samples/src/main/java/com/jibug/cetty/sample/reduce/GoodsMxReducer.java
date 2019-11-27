package com.jibug.cetty.sample.reduce;

import com.alibaba.fastjson.JSONArray;
import com.basic.support.commons.business.json.util.FastJsonUtil;
import com.basic.support.commons.business.logger.LogUtil;
import com.google.common.base.Joiner;
import com.jibug.cetty.sample.constants.AreaTypeEnum;
import com.jibug.cetty.sample.entity.MlGoodsMxPo;
import com.jibug.cetty.sample.entity.MlGoodsTypePo;
import com.jibug.cetty.sample.service.MlGoodsMxService;
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
public class GoodsMxReducer extends MlReducer<MlGoodsMxPo,MlGoodsTypePo> {

    private static Joiner joiner = Joiner.on("|");
    private static Map<String, String> oldMxMap = new ConcurrentHashMap<>(16);

    @Autowired
    private MlGoodsTypeService mlGoodsTypeService;
    @Autowired
    private MlGoodsMxService mlGoodsMxService;

    @Override
    protected void saveType(List<MlGoodsTypePo> mlList) throws Exception {
        List<MlGoodsTypePo> oldList = mlGoodsTypeService.queryListByArea(AreaTypeEnum.MX.code());
        List<MlGoodsTypePo> saveRsult = new ArrayList<>();
        if (mlList == null || mlList.size()==0) {
            LogUtil.error("ML 类型结果为空");
            return;
        }
        if (oldList == null) {
            try {
                mlGoodsTypeService.batchInseartOrUpdate(mlList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        oldList.forEach(old->{
            String key = this.getKey(old.getSeries(),old.getEntry());
            if(!oldMxMap.containsKey(key)){
                oldMxMap.put(key,key);
            }
        });
        mlList.forEach(ty->{
            if(!oldMxMap.containsKey(this.getKey(ty.getSeries(),ty.getEntry())) ){
                saveRsult.add(ty);
            }
        });
        if(saveRsult!=null && saveRsult.size()>0){
            mlGoodsTypeService.batchInseartOrUpdate(mlList);
        }
    }

    @Override
    protected void saveGoods(JSONArray arr){
        List<MlGoodsMxPo> mlList = FastJsonUtil.toBeanList(arr, MlGoodsMxPo.class);
        if(mlList==null || mlList.size()<=0){
            return;
        }
        try {
            mlGoodsMxService.saveAll(mlList);
        } catch (Exception e) {
            LogUtil.error("{}",e.getStackTrace());
            e.printStackTrace();
        }
    }


}
