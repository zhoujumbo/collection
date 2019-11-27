package com.jibug.cetty.sample.handler;

import com.alibaba.fastjson.JSONObject;
import com.basic.support.commons.business.json.util.FastJsonUtil;
import com.basic.support.commons.business.logger.LogUtil;
import com.basic.support.commons.business.util.DateUtil;
import com.google.common.collect.Lists;
import com.jibug.cetty.core.Page;
import com.jibug.cetty.core.Seed;
import com.jibug.cetty.core.handler.HandlerContext;
import com.jibug.cetty.sample.constants.MlGoodsConstants;
import com.jibug.cetty.sample.constants.UrlTypeEnum;
import com.jibug.cetty.sample.entity.MlGoodsTypePo;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 页面解析
 *
 */
public abstract class MlPageHandler<T> extends BasePageHandler {

    protected static final Pattern PAGE_TYPE_REGEX_PATTERN = Pattern.compile("#menu=.{1,}");
    protected static final Pattern PAGE_GOODS_REGEX_PATTERN = Pattern.compile("/MLM-.{1,}");
    protected static final Pattern PAGE_GOODSLIST_REGEX_PATTERN = Pattern.compile("/_Desde_(\\d+){1,}");

    protected static final Pattern AMOUNT_PATTERN = Pattern.compile("(\\d+\\.\\d+)");
    protected static final Pattern AMOUNT_2_PATTERN = Pattern.compile("(\\d+)");

    @Override
    public void process(HandlerContext ctx, Page page) {
        parseRoute(ctx, page);
    }

    @Override
    protected abstract void parseRoute(HandlerContext ctx, Page page);

    /**
     * 解析商品类型
     * @param ctx
     * @param page
     */
    protected synchronized void parseGoodsType(HandlerContext ctx, Page page, Short area){
        Matcher matcher = PAGE_TYPE_REGEX_PATTERN.matcher(page.getUrl());
        if (!matcher.find()) {
            return;
        }

        Document document = page.getDocument();
        if (document == null) {
            return;
        }
        Elements mainElements  = document.select("main#root-app");
        if(mainElements==null){
            LogUtil.error("类型页面变更");
            return;
        }
        Elements mainList  = mainElements.first().select("div.allcategories div.allcategories__main div.categoriesComponent div.categories__container");

        if(mainList==null || mainList.isEmpty()){
            LogUtil.error("系列类型列表为空");
            return;
        }

        List<String> mlList = Lists.newArrayList();

        List<Seed> seeds = new ArrayList<>();
        mainList.forEach(obj->{
            Element seriesDom = obj.select("h2.categories__title>a").first();
            StringBuffer seriesName = new StringBuffer();
            if(seriesDom!=null){
                seriesName.append(seriesDom.text());
            }

            Elements ulItem = obj.select("ul>li>a");
            if(ulItem==null || ulItem.isEmpty()){
                LogUtil.error("规格类型列表为空");
                return;
            }
            ulItem.forEach(ooo->{
                String typeName = ooo.text().trim();
                String typeUrl = ooo.attr("href").trim();
                mlList.add(FastJsonUtil.bean2JsonStr(
                        new MlGoodsTypePo()
                                .setArea(area)
                                .setSeries(seriesName.toString())
                                .setEntry(typeName)
                                .setCreatTime(DateUtil.currentTime())
                ));
                if(StringUtils.isNotEmpty(typeUrl)){
                    Seed seed = new Seed(typeUrl);
                    seed.putAttach("via", page.getSeed().getAttach("via"));
                    seed.putAttach("urlType", UrlTypeEnum.TYPE.message());
                    seed.putAttach("pageSize", MlGoodsConstants.PAGESIZE);

                    seeds.add(seed);
                }
            });
        });

        page.addNextSeed(seeds);

        page.getResult().putField("goodsType", mlList.toString());

        ctx.fireReduce(page);
    }


    /**
     * 解析商品
     * @param ctx
     * @param page
     */
    protected void parseGoodsList(HandlerContext ctx, Page page){
        Document document = page.getDocument();
        if (document == null) {
            return;
        }
        Elements mainElements  = document.select("section#results-section");
        Elements typeElements  = document.select("div#inner-main>aside.filters div.breadcrumb h1");
        Elements pageElements  = document.select("section#results-section div.pagination__container ul.andes-pagination ");

        Elements searchResults;
        if(mainElements!=null){
            searchResults  = mainElements.first().select("ol#searchResults>li.results-item>div.rowItem");
        }else{
            return;
        }

        List<String> goodsList = Lists.newArrayList();
        if(searchResults==null || searchResults.isEmpty()){
            return;
        }
        searchResults.forEach(result->{
            Elements txtDom = result.select("div.item__info-container");
            if(txtDom==null){
                txtDom = result.select("a.item__info-link");
            }
            if(txtDom==null){
                return;
            }
            JSONObject goodsObj = new JSONObject();

            String goodsId = result.attr("id");
            if(StringUtils.isNotEmpty(goodsId)){
                goodsObj.put("goodsId",goodsId);
            }

            if(txtDom != null){
                // url
                Elements urlDom = txtDom.first().select("div.item__info>h2.item__title>a");
                if(urlDom == null){
                    urlDom = txtDom.first().select("div.item__info>h2.item__title");
                }
                // price
                Elements priceDom = txtDom.first().select("div.item__info>div.price__container");
                if(priceDom == null){
                    priceDom = txtDom.first().select("div.item__info");
                }
                // sales
                Elements salesVolumeDom = txtDom.first().select("div.item__info>div.item__stack_column");
                if(salesVolumeDom == null){
                    salesVolumeDom = txtDom.first().select("div.item__info div.item__status");
                }

                if(urlDom != null){
                    Elements titleDom = urlDom.first().getElementsByTag("span");

                    Element goodsUrlEle = urlDom.first();
                    if(!goodsUrlEle.tagName().equals("a")){
                        goodsUrlEle = txtDom.first();
                    }
                    if(goodsUrlEle!=null){
                        String goodsUrl = urlDom.first().attr("href");
                        if(goodsUrl.length() < 1000){
                            //ml.setUrl1(goodsUrl);
                            goodsObj.put("url1",goodsUrl);

                        }else{
                            //ml.setUrl1(goodsUrl.substring(0,1000));
                            goodsObj.put("url1",goodsUrl.substring(0,1000));
                        }
                    }
                    if(titleDom != null){
                        Element t = titleDom.first();
                        if(t != null){
                            String title = t.ownText().trim();
                            //ml.setTitle(title);
                            goodsObj.put("title",title);

                            if(typeElements != null){
                                String series = typeElements.text().trim();
                                //ml.setSeries(series);
                                goodsObj.put("series",series);
                            }else{
                                //ml.setSeries(title);
                                goodsObj.put("series",title);
                            }
                        }
                    }
                }

                if(priceDom !=null){
                    Element pri = priceDom.first().select("div.item__price").select("span.price__fraction").first();
                    if(pri != null){
                        String price = pri.text().trim();
                        price = extractAmountMsg(price);
                        //ml.setActualPrice(price);
                        goodsObj.put("actualPrice",price);
                    }
                }

                if(salesVolumeDom != null){
                    // sales
                    Element sales = salesVolumeDom.first().select("div.item__condition").first();
                    if(sales != null){
                        String salesVolume = sales.text().trim();
                        salesVolume = extractAmountMsg(salesVolume);
                        //ml.setSalesVolume(salesVolume);
                        goodsObj.put("salesVolume",salesVolume);
                    }

                    // shipping
                    Element shipping = salesVolumeDom.first().select("div.item__shipping>p").first();
                    if(shipping != null){
                        // 免邮
                        String isfree = shipping.text().trim();
                        if (isNotEmpty(isfree) && isfree.length()>2) {
                            goodsObj.put("postFree","1");
                        }else{
                            goodsObj.put("postFree","0");
                        }
                        // 海外仓
                        if(shipping.hasClass("item--has-fulfillment")){
                            goodsObj.put("osWarehouse","1");
                        }else{
                            goodsObj.put("osWarehouse","0");
                        }
                    }
                }
            }

            if(StringUtils.isNotEmpty(goodsObj.getString("salesVolume"))){
//                ml.setCreatTime(DateUtil.currentTime());
                goodsObj.put("creatTime",DateUtil.currentTime());
                goodsList.add(goodsObj.toJSONString());
            }

        });
        String nextPageUrl = "";

        if(pageElements!=null){
            try{
                Elements nextPageDom = pageElements.first().select("li.andes-pagination__button--next a");
                if(nextPageDom!=null){
                    nextPageUrl = nextPageDom.first().attr("href").trim();
                }
            }catch (Exception e){
                LogUtil.warn(e.getStackTrace().toString());
            }
        }
        if(StringUtils.isNotEmpty(nextPageUrl)){
            Seed seed = new Seed(nextPageUrl);
            seed.putAttach("urlType", UrlTypeEnum.GOODS_LIST.message());
            seed.putAttach("pageSize", MlGoodsConstants.PAGESIZE);
            page.addNextSeed(seed);
//        }
        }

        page.getResult().putField("goodsList", goodsList.toString());

        ctx.fireReduce(page);
    }


    public String extractAmountMsg(String ptCasinoMsg){
        String returnAmount = "";
        ptCasinoMsg = ptCasinoMsg.replace(",", "");
        Matcher m = AMOUNT_PATTERN.matcher(ptCasinoMsg);
        if(m.find()){
            returnAmount = m.group(1);
        }else{
            m = AMOUNT_2_PATTERN.matcher(ptCasinoMsg);
            if(m.find()){
                returnAmount = m.group(1);
            }
        }
        return returnAmount;
    }

    /**
     * From java lang3
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * From java lang3
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

}
