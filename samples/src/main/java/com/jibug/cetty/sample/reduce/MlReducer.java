package com.jibug.cetty.sample.reduce;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.basic.support.commons.business.json.util.FastJsonUtil;
import com.basic.support.commons.business.logger.LogUtil;
import com.google.common.base.Joiner;
import com.jibug.cetty.core.Page;
import com.jibug.cetty.core.Result;
import com.jibug.cetty.core.handler.HandlerContext;
import com.jibug.cetty.core.handler.ReduceHandlerAdapter;
import com.jibug.cetty.sample.entity.MlGoodsTypePo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * reduce聚合处理器
 *
 * @author heyingcai
 */
public abstract class MlReducer<T,D> extends ReduceHandlerAdapter {

    private static Joiner joiner = Joiner.on("|");
    private static Map<String, String> oldBrMap = new ConcurrentHashMap<>(16);


    @Override
    public void reduce(HandlerContext ctx, Page page) {
        Result result = page.getResult();
        Map<String, Object> fieldResult = result.getFieldResult();
        if (fieldResult.isEmpty()) {
            return;
        }
        // type
        reduceType(fieldResult);
        // goods
        reduceGoods(fieldResult);

    }



    protected void reduceType(Map<String, Object> fieldResult){

        try{
            String objects = (String) fieldResult.get("goodsType");
            if(StringUtils.isNotEmpty(objects)){
                JSONArray arr = JSON.parseArray(objects);
                List<MlGoodsTypePo> mlList = FastJsonUtil.toBeanList(arr, MlGoodsTypePo.class);
                if (mlList == null || mlList.size()==0) {
                    LogUtil.error("MlGoodsTypeList 类型结果为空");
                    return;
                }
                saveType(mlList);
            }
        }catch (Exception e){
            LogUtil.error("结果处理异常：： 类型....{}",e.getStackTrace());
        }

    }

    protected abstract void saveType(List<MlGoodsTypePo> mlList) throws Exception;

    protected void reduceGoods(Map<String, Object> fieldResult){

        try{
            String objects = (String) fieldResult.get("goodsList");
            if(StringUtils.isNotEmpty(objects)){
                JSONArray arr = JSON.parseArray(objects);
                saveGoods(arr);
            }

        }catch (Exception e){
            LogUtil.error("结果处理异常：： 列表....{}",e.getStackTrace());
        }
    }

    protected abstract void saveGoods(JSONArray arr);


    protected String getKey(String series,String entry){
        return joiner.join(series,entry);
    }
}
