package com.jibug.cetty.sample.handler;

import com.jibug.cetty.core.Page;
import com.jibug.cetty.core.Seed;
import com.jibug.cetty.core.handler.HandlerContext;
import com.jibug.cetty.core.log.LogUtil;
import com.jibug.cetty.sample.constants.AreaTypeEnum;
import com.jibug.cetty.sample.entity.MlGoodsBr;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

/**
 * 抓取
 */
@Component
public class MlGoodsBrPageHandler extends MlPageHandler<MlGoodsBr> {

    @Override
    protected void parseRoute(HandlerContext ctx, Page page) {
        try{
            String pageUrl = page.getUrl();
            Seed seed = page.getSeed();
            String urlType = (String) seed.getAttach("urlType");
            LogUtil.info("当前处理的URL是MlGoodsBr[{}]",pageUrl);

            Matcher matcher = PAGE_TYPE_REGEX_PATTERN.matcher(page.getUrl());
            Matcher matcher2 = PAGE_GOODS_REGEX_PATTERN.matcher(page.getUrl());
            if (matcher.find()) {
                LogUtil.info("当前处理的是MlGoodsBr类别[{}]",pageUrl);
                parseGoodsType(ctx, page, AreaTypeEnum.BR.message());
            }else
            if (matcher2.find()) {
                return;
            }else{
                LogUtil.info("当前处理的是MlGoodsBr商品[{}]",pageUrl);
                parseGoodsList(ctx, page);
            }
        }catch (Exception e){
            LogUtil.info("异常");
            e.printStackTrace();
        }
    }






}
