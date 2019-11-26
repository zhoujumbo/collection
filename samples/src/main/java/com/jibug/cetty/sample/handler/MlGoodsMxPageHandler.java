package com.jibug.cetty.sample.handler;

import com.jibug.cetty.core.Page;
import com.jibug.cetty.core.Seed;
import com.jibug.cetty.core.handler.HandlerContext;
import com.jibug.cetty.core.log.LogUtil;
import com.jibug.cetty.sample.constants.AreaTypeEnum;
import com.jibug.cetty.sample.entity.MlGoodsMx;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

/**
 * 抓取
 */
@Component
public class MlGoodsMxPageHandler extends MlPageHandler<MlGoodsMx> {

    @Override
    protected void parseRoute(HandlerContext ctx, Page page) {
        try{
            String pageUrl = page.getUrl();
            Seed seed = page.getSeed();
            String urlType = (String) seed.getAttach("urlType");
            LogUtil.info("当前处理的URL是MlGoodsMx[{}]",pageUrl);

            Matcher matcher = PAGE_TYPE_REGEX_PATTERN.matcher(page.getUrl());
            Matcher matcher2 = PAGE_GOODS_REGEX_PATTERN.matcher(page.getUrl());
            if (matcher.find()) {
                LogUtil.info("当前处理的是MlGoodsMx[{}]",pageUrl);
                parseGoodsType(ctx, page, AreaTypeEnum.MX.message());
            }else
            if (matcher2.find()) {
                return;
            }else{
                LogUtil.info("当前处理的是MlGoodsMx[{}]",pageUrl);
                parseGoodsList(ctx, page);
            }
        }catch (Exception e){
            LogUtil.info("异常");
            e.printStackTrace();
        }
    }






}
