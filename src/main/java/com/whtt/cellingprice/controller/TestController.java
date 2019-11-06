package com.whtt.cellingprice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: wbh
 * @Date: 1019/11/6 15:19
 * @Description:
 */
@Controller
@RequestMapping
public class TestController {

    @RequestMapping(value = "/home")
    public String home(){
          return "home";
    }
    @RequestMapping(value = "/index")
    public String index(){
          return "index";
    }

}
