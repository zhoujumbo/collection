package com.jibug.cetty.sample.schedule.tasks;

import com.jibug.cetty.core.log.LogUtil;
import com.jibug.cetty.core.utils.DateUtil;
import com.jibug.cetty.sample.common.jdbctemplate.CommonDao;
import com.jibug.cetty.sample.common.jdbctemplate.SqlParaBuffer;
import com.jibug.cetty.sample.constants.MlGoodsConstants;
import com.jibug.cetty.sample.dao.MlGoodsBrDao;
import com.jibug.cetty.sample.dao.MlGoodsMxDao;
import com.jibug.cetty.sample.dao.TableManageDao;
import com.jibug.cetty.sample.entity.MlGoodsBr;
import com.jibug.cetty.sample.entity.TableManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataUnit;

import java.util.List;

/**
 * 处理统计结果
 */
@Service
public class MlTask {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private TableManageDao tableManageDao;
    @Autowired
    private MlGoodsMxDao mlGoodsMxDao;
    @Autowired
    private MlGoodsBrDao mlGoodsBrDao;


    /**
     * mx
     */
    @Transactional
    public void getMxTheResult(){
        LogUtil.warn("------ MX结果统计开始 ------");

        // 判断本次抓取数据量
        SqlParaBuffer sql1 = new SqlParaBuffer();
        sql1.append("SELECT COUNT(1) FROM ml_goods_mx;");
        int cnt = commonDao.getTotal(sql1.getSql());
        if(cnt==0){
            LogUtil.error("数据统计  原始数据表为空");
            return;
        }

        // 查询最近一次统计结果
        List<TableManage> tableManageList = tableManageDao.getTopOne("MX");
        if(tableManageList==null || tableManageList.size()==0){
            simpleTableCount("MX",cnt);
        }else{
            TableManage tableManage = tableManageList.stream().findFirst().get();
            incrementTableCount("MX",tableManage.getTable_name(),cnt);
        }

    }


    /**
     * BR
     */
    @Transactional
    public void getBrTheResult(){
        LogUtil.warn("------ BR结果统计开始 ------");

        // 判断本次抓取数据量
        SqlParaBuffer sql1 = new SqlParaBuffer();
        sql1.append("SELECT COUNT(1) FROM ml_goods_br;");
        int cnt = commonDao.getTotal(sql1.getSql());
        if(cnt==0){
            LogUtil.error("数据统计  原始数据表为空");
            return;
        }

        // 查询最近一次统计结果
        List<TableManage> tableManageList = tableManageDao.getTopOne("BR");
        if(tableManageList==null || tableManageList.size()==0){
            simpleTableCount("BR",cnt);
        }else{
            TableManage tableManage = tableManageList.stream().findFirst().get();
            incrementTableCount("MX",tableManage.getTable_name(),cnt);
        }
    }

    @Transactional
    public void incrementTableCount(String type,String tbName, int cnt){
        String newTbName = "";
        String fromTb = "";
        switch (type){
            case "MX":
                newTbName = MlGoodsConstants.TB_RESULT_MX_PREFIX+ DateUtil.ymdhmsFormatEasy();
                fromTb = "ml_goods_mx";
                break;
            case "BR":
                newTbName = MlGoodsConstants.TB_RESULT_BR_PREFIX+ DateUtil.ymdhmsFormatEasy();
                fromTb = "ml_goods_br";
                break;
            default :
                break;
        }
        // 生成新的统计
        StringBuffer sqlParaBuffer = new StringBuffer();
        sqlParaBuffer.append("CREATE TABLE ");
        sqlParaBuffer.append(newTbName);
        sqlParaBuffer.append(" SELECT  a3.goods_id,a3.title ,a3.series, a3.actual_price");
        sqlParaBuffer.append(" ,a3.min_time, a3.min ,date_add(a3.min_time, interval (a3.total-1) day) as max_time ,a3.max ");
        sqlParaBuffer.append(" ,a3.sub, a3.post_free, a3.os_warehouse FROM  ( ");
        sqlParaBuffer.append(" select a2.goods_id,a2.title ,a2.series, a2.actual_price, count(1) as total ,a2.creat_time as min_time ");
        sqlParaBuffer.append(" ,MIN(a2.sales_volume+0) as min,MAX(a2.sales_volume+0) as max,(MAX(a2.sales_volume+0) - MIN(a2.sales_volume+0)) as sub ");
        sqlParaBuffer.append(" ,MAX(a2.post_free+0) as post_free,MAX(a2.os_warehouse+0) as os_warehouse  from ( ");
        sqlParaBuffer.append(" select * from ( ");
        sqlParaBuffer.append(" SELECT `goods_id`, `title`, `series`, actual_price+'' as actual_price, max+'' as 'sales_volume' , date_format(max_time ,'%Y-%m-%d' ) creat_time");
        sqlParaBuffer.append(" ,IFNULL(post_free,0) post_free,IFNULL(os_warehouse,0) os_warehouse FROM ");
        sqlParaBuffer.append(tbName);
        sqlParaBuffer.append(" UNION ALL  ");
        sqlParaBuffer.append(" SELECT goods_id,title,series,actual_price,sales_volume,date_format(creat_time ,'%Y-%m-%d' ) creat_time ");
        sqlParaBuffer.append(" ,IFNULL(post_free,0) post_free,IFNULL(os_warehouse,0) os_warehouse FROM  ");
        sqlParaBuffer.append(fromTb);
        sqlParaBuffer.append(" ) as temp ");
        sqlParaBuffer.append(" group by goods_id,creat_time ");
        sqlParaBuffer.append(" ) a2 group by a2.goods_id,a2.title having count(1)>0 ");
        sqlParaBuffer.append(" ) as a3  ");
        sqlParaBuffer.append(" ORDER BY a3.series ASC,a3.sub DESC");
//        System.out.println(sqlParaBuffer.toString());
        boolean flag = false;
        try{
            commonDao.executSql(sqlParaBuffer.toString());
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(flag){
            // 保存新的结果信息
            TableManage addNewTbInfo = new TableManage();
            addNewTbInfo.setOrigin(type);
            addNewTbInfo.setTable_name(newTbName);
            addNewTbInfo.setLength(String.valueOf(cnt));
            addNewTbInfo.setCreat_time(DateUtil.ymdhmsFormatEasy());
            addNewTbInfo.setStatus(Short.valueOf("0"));
            tableManageDao.saveAndFlush(addNewTbInfo);
            // 删除爬去原数据
    //        mlGoodsMxDao.deleteAll();
            switch (type){
                case "MX":
                    mlGoodsMxDao.deleteAll();
                    break;
                case "BR":
                    mlGoodsBrDao.deleteAll();
                    break;
                default :
                    break;
            }
        }

    }

    @Transactional
    public void simpleTableCount(String type, int cnt){
        String newTbName = "";
        String fromTb = "";
        switch (type){
            case "MX":
                newTbName = MlGoodsConstants.TB_RESULT_MX_PREFIX+ DateUtil.ymdhmsFormatEasy();
                fromTb = "ml_goods_mx";
                break;
            case "BR":
                newTbName = MlGoodsConstants.TB_RESULT_BR_PREFIX+ DateUtil.ymdhmsFormatEasy();
                fromTb = "ml_goods_br";
                break;
            default :
            break;
        }
//        tableManageDao.creatTableByName(newTbName, tableManage.getTable_name(), "ml_goods_br");
        StringBuffer sqlParaBuffer = new StringBuffer();
        sqlParaBuffer.append(" CREATE TABLE  ");
        sqlParaBuffer.append(newTbName);
        sqlParaBuffer.append(" SELECT  a3.id,a3.goods_id,a3.title ,a3.series, a3.actual_price ");
        sqlParaBuffer.append(" ,a3.min_time, a3.min ,date_add(a3.min_time, interval (a3.total-1) day) as max_time ,a3.max ");
        sqlParaBuffer.append(" ,a3.sub,a3.post_free,a3.os_warehouse    FROM  ( ");
        sqlParaBuffer.append(" select a2.id,a2.goods_id,a2.title ,a2.series, a2.actual_price, count(1) as total ,a2.creat_time as min_time ,a2.post_free,a2.os_warehouse");

        sqlParaBuffer.append(" ,MIN(a2.sales_volume+0) as min,MAX(a2.sales_volume+0) as max,(MAX(a2.sales_volume+0) - MIN(a2.sales_volume+0)) as sub  ");
        sqlParaBuffer.append(" from ( ");
        sqlParaBuffer.append(" select * from ( ");
        sqlParaBuffer.append(" SELECT id,goods_id,title,series,actual_price,sales_volume,date_format(creat_time ,'%Y-%m-%d' ) creat_time,IFNULL(post_free,0) post_free,IFNULL(os_warehouse,0) os_warehouse ");
        sqlParaBuffer.append(" FROM ");
        sqlParaBuffer.append(fromTb);
        sqlParaBuffer.append(" ) as temp  ");
        sqlParaBuffer.append(" group by goods_id,creat_time ");
        sqlParaBuffer.append(" ) a2 group by a2.goods_id,a2.title having count(1)>0  ");
        sqlParaBuffer.append(" ) as a3 ");
        sqlParaBuffer.append(" ORDER BY a3.series ASC,a3.sub DESC ");
        System.out.println(sqlParaBuffer.toString());
        boolean flag = false;
        try{
            commonDao.executSql(sqlParaBuffer.toString());
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        if(flag){
            // 保存新的结果信息
            TableManage addNewTbInfo = new TableManage();
            addNewTbInfo.setOrigin(type);
            addNewTbInfo.setTable_name(newTbName);
            addNewTbInfo.setLength(String.valueOf(cnt));
            addNewTbInfo.setCreat_time(DateUtil.ymdhmsFormatEasy());
            addNewTbInfo.setStatus(Short.valueOf("0"));

            tableManageDao.saveAndFlush(addNewTbInfo);
            // 删除爬去原数据
            switch (type){
                case "MX":
                    mlGoodsMxDao.deleteAll();
                    break;
                case "BR":
                    mlGoodsBrDao.deleteAll();
                    break;
                default :
                    break;
            }
        }

    }
}
