package com.jibug.cetty.sample.web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author jb.zhou
 * @Date 2019/11/26
 * @Version 1.0
 */
@RestController
@RequestMapping("/")
public class IndexController{

//    @PostMapping(value = "/list")
//    public List<GoodsResponse> getGoods(GoodsQuery goodsQuery){
//        List<Goods> goodsList = new ArrayList<>();
//        try{
//            goodsList = goodsService.getGoods(goodsQuery);
//        }catch(Exception e){
//            log.error("获取商品列表数据异常");
//        }
//        return Optional.ofNullable(goodsList)
//                .map(list-> list.stream()
//                        .map(g->{
//                            GoodsResponse response = new GoodsResponse();
//                            BeanUtils.copyProperties(g,response);
//                            return response;
//                        }).collect(Collectors.toList()))
//                .orElse(Collections.emptyList());
//    }



}
