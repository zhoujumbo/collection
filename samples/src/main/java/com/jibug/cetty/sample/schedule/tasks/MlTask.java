package com.jibug.cetty.sample.schedule.tasks;

import com.jibug.cetty.sample.container.BrSourceDataContainer;
import com.jibug.cetty.sample.container.MxSourceDataContainer;
import com.jibug.cetty.sample.entity.domain.MlGoodsBr;
import com.jibug.cetty.sample.entity.domain.MlGoodsMx;
import com.jibug.cetty.sample.service.MlGoodsBrService;
import com.jibug.cetty.sample.service.MlGoodsMxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理统计结果
 */
@Service
public class MlTask {


    @Autowired
    private MxSourceDataContainer mxContainer;
    @Autowired
    private BrSourceDataContainer brContainer;

    @Autowired
    private MlGoodsMxService mlGoodsMxService;
    @Autowired
    private MlGoodsBrService mlGoodsBrService;


    public void saveMx() throws InterruptedException {
        List<MlGoodsMx> mlGoodsMxList = new ArrayList<>();
        while (true){
            if(!mxContainer.isEmpty()){
                if(mlGoodsMxList.size()<10000){
                    MlGoodsMx mlGoodsMx = mxContainer.poll();
                    mlGoodsMxList.add(mlGoodsMx);
                }else{
                    mlGoodsMxService.insertBatch(mlGoodsMxList);
                    break;
                }
            }else{
                if(!mlGoodsMxList.isEmpty()){
                    mlGoodsMxService.insertBatch(mlGoodsMxList);
                }
                break;
            }
        }
    }


    public void saveBr() throws InterruptedException {
        List<MlGoodsBr> mlGoodsBrList = new ArrayList<>();
        while (true){
            if(!brContainer.isEmpty()){
                if(mlGoodsBrList.size()<10000){
                    MlGoodsBr mlGoodsBr = brContainer.poll();
                    mlGoodsBrList.add(mlGoodsBr);
                }else{
                    mlGoodsBrService.insertBatch(mlGoodsBrList);
                    break;
                }
            }else{
                if(!mlGoodsBrList.isEmpty()){
                    mlGoodsBrService.insertBatch(mlGoodsBrList);
                }
                break;
            }
        }
    }


    public void delOldDataMx(){
        mlGoodsMxService.deleteOldData();
    }

    public void delOldDataBr(){
        mlGoodsBrService.deleteOldData();
    }


}
