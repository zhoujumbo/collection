package com.jibug.cetty.sample.constants;


import com.basic.support.commons.business.redis.RedisKeyUtil;

public class MlGoodsConstants {

    public final static int PAGESIZE = 50;

    public final static String KEY_ML_MX = RedisKeyUtil.getKeyWithColumn("ml","mx","goods","list");
    public final static String KEY_ML_BR = RedisKeyUtil.getKeyWithColumn("ml","br","goods","list");

    public final static String KEY_ML_MX_RESULT = RedisKeyUtil.getKeyWithColumn("ml","mx","result","list");
    public final static String KEY_ML_BR_RESULT = RedisKeyUtil.getKeyWithColumn("ml","br","result","list");

    public final static String MX_SQL = "INSERT INTO `mercadolibre`.`ml_goods_mx` (`goods_id`, `title`, `series`, `actual_price`, `url1`, `creat_time`, `sales_volume`) VALUES (?,?,?,?,?,?,?);";
    public final static String BR_SQL = "INSERT INTO `mercadolibre`.`ml_goods_br` (`goods_id`, `title`, `series`, `actual_price`, `url1`, `creat_time`, `sales_volume`) VALUES (?,?,?,?,?,?,?);";

    public final static String TB_RESULT_MX_PREFIX = "ml_goods_mx_result_";
    public final static String TB_RESULT_BR_PREFIX = "ml_goods_br_result_";

}
