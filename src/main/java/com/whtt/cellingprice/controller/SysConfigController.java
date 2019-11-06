package com.whtt.cellingprice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.entity.pojo.SysConfig;
import com.whtt.cellingprice.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

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
    @PostMapping(value = "/updateDeductIntegral")
    public Object updateDeductIntegral(@RequestParam @NotNull(message = "扣除的积分金额不能为空!") Integer deductIntegral){
        //获取更改结果
        boolean result = configService.updateDeductIntegral(deductIntegral);
        if(result){
            return CommonResult.success();
        }

        return CommonResult.failed("积分设置更改失败");
    }

    @RequestMapping(value = "/integral")
    public Object integral() {
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<SysConfig>().eq("config_key", "deduct_integral");
        SysConfig deductConfig = configService.getOne(queryWrapper);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("config/integral");
        mav.addObject("config",deductConfig);
        return mav;
    }

}

