package com.whtt.cellingprice.controller;


import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.entity.pojo.SysConfig;
import com.whtt.cellingprice.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 系统配置表 前端控制器
 * </p>
 *
 * @author
 * @since 2019-11-06
 */
@Controller
@RequestMapping("/admin/sysConfig")
public class SysConfigController {

    @Autowired
    private SysConfigService configService;

    //更改每次顶价扣除的用户的积分
    @ResponseBody
    @PostMapping(value = "/updateConfig")
    public Object updateDeductIntegral(@RequestBody List<SysConfig> configList){
        configList.forEach(config -> configService.updateById(config));
        return CommonResult.failed("积分设置更改失败");
    }

    @GetMapping
    public Object integral() {
        List<SysConfig> configList = configService.list();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("config/list");
        mav.addObject("configList",configList);
        return mav;
    }

}

