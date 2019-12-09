package com.jibug.cetty.sample.reduce;

import com.alibaba.fastjson.JSONArray;
import com.basic.support.commons.business.json.util.FastJsonUtil;
import com.basic.support.commons.business.logger.LogUtil;
import com.google.common.base.Joiner;
import com.jibug.cetty.sample.constants.AreaTypeEnum;
import com.jibug.cetty.sample.container.BrSourceDataContainer;
import com.jibug.cetty.sample.entity.MlGoodsBrPo;
import com.jibug.cetty.sample.entity.MlGoodsTypePo;
import com.jibug.cetty.sample.entity.domain.MlGoodsBr;
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
public class GoodsBrReducer extends MlReducer<MlGoodsBrPo,MlGoodsTypePo> {

    private static Joiner joiner = Joiner.on("|");
    private static Map<String, String> oldBrMap = new ConcurrentHashMap<>(16);

    @Autowired
    private MlGoodsTypeService mlGoodsTypeService;
    @Autowired
    private MlGoodsBrService mlGoodsBrService;
    @Autowired
    private BrSourceDataContainer brContainer;

    @Override
    protected void saveType(List<MlGoodsTypePo> mlList) throws Exception {
        List<MlGoodsTypePo> oldList = mlGoodsTypeService.queryListByArea(AreaTypeEnum.BR.code());
        List<MlGoodsTypePo> saveRsult = new ArrayList<>();
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
//        List<MlGoodsBrPo> mlList = FastJsonUtil.toBeanList(arr, MlGoodsBrPo.class);
        List<MlGoodsBr> mlList = FastJsonUtil.toBeanList(arr, MlGoodsBr.class);
        if(!mlList.isEmpty()){
            mlList.parallelStream().forEach(brContainer::offer);
        }
//        try {
////            mlGoodsBrService.saveAll(mlList);
//        } catch (Exception e) {
//            LogUtil.error("{}",e.getStackTrace());
//            e.printStackTrace();
//        }
    }


}
