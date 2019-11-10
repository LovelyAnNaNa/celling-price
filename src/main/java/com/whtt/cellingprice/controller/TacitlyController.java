package com.whtt.cellingprice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class TacitlyController {
    @GetMapping(value = "/")
    public String login(){
        return "/login";
    }

}
