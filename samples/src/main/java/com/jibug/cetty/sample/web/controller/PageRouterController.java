package com.jibug.cetty.sample.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageRouterController {
    private Logger log = LoggerFactory.getLogger(getClass());


    @RequestMapping("/index")
    public String defToIndex(Model model){
//        if(authentication==null){
//            return "redirect:/login";
//        }
//        model.addAttribute("username",authentication.getName());
//        model.addAttribute("urlMaps",backProperties.getUrlMap().getUrlMap());
//        model.addAttribute("staticUrl",backProperties.getUrlMap().getStaticUrl());
//        log.info("进入默认页面");
//        log.info("urlMaps{{}}",backProperties.getUrlMap().getUrlMap());
        return "index";
    }

//    @GetMapping("/")
//    public String toIndex(){
//        return "redirect:/home";
//    }

//    @RequestMapping("/login")
//    public String toLogin(Model model){
//        model.addAttribute("staticUrl",backProperties.getUrlMap().getStaticUrl());
//        return "login";
//    }

}
