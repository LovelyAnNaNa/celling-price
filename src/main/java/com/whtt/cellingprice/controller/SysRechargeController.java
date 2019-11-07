package com.whtt.cellingprice.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.whtt.cellingprice.entity.pojo.SysRecharge;
import com.whtt.cellingprice.service.SysCustomerService;
import com.whtt.cellingprice.service.SysRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wbh
 * @since 2019-11-07
 */
@Controller
@RequestMapping("/admin/sysRecharge")
public class SysRechargeController {

    @Autowired
    private SysCustomerService customerService;
    @Autowired
    private SysRechargeService rechargeService;

    @ResponseBody
    @PostMapping(value = "/list")
    public Object list(@RequestParam(value = "page",required = false) Integer page,@RequestParam(value = "limit",required = false) Integer limit,
                       @RequestParam(value = "customerName",required = false) String customerName,
                       @RequestParam(value = "rangeIntegral",required = false) String rangeIntegral,
                       @RequestParam(value = "startTime",required = false) String startTime,
                       @RequestParam(value = "endTime",required = false) String endTime){
        //获取充值列表
        List<SysRecharge> rechargeList = rechargeService.getRechargeList(page,limit,customerName,rangeIntegral,startTime,endTime);

        //封装为layui表格可以解析的对象
        PageData<SysRecharge> pageData = new PageData<>(rechargeList);
        return pageData;
    }

    @ResponseBody
    @PostMapping(value = "/saveAdd")
    public Object saveAdd(@RequestBody SysRecharge newRecharge){
        //验证参数
        newRecharge.validateParams();

        //获取要更改的积分用户的id
        boolean result = rechargeService.addRecharge(newRecharge);
        if (result) {
            return CommonResult.success();
        }

        return CommonResult.failed("积分更改失败,请稍后重试");
    }

    @GetMapping
    public String listPage(){
          return "recharge/list";
    }

    @GetMapping(value = "/add")
    public Object add(@RequestParam @NotNull(message = "用户id不能为空!") Integer customerId,
                      ModelAndView mav){
        SysCustomer customerInfo = customerService.getById(customerId);
        if (customerInfo == null) {
            return JSON.toJSONString(CommonResult.failed("所传用户id不正确!"));
        }

        mav.setViewName("/recharge/add");
        mav.addObject("customerInfo",customerInfo);
        return mav;
    }
}

