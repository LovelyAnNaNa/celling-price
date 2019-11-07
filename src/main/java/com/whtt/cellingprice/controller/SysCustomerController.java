package com.whtt.cellingprice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.config.DataConfig;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.whtt.cellingprice.service.SysCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author wbh
 * @since 2019-11-06
 */
@Controller
@RequestMapping("/admin/sysCustomer")
public class SysCustomerController {

    @Autowired
    private SysCustomerService customerService;

    //获取扣除积分的信息
    @ResponseBody
    @PostMapping(value = "/getDeductInfo")
    public Object getDeductInfo(){
        HashMap<String, Object> infoMap = new HashMap<>();
        //顶价成功扣除的积分
        infoMap.put("cellingIntegral", DataConfig.cellingIntegral);
        //违约扣除的积分
        infoMap.put("violateIntegral", DataConfig.violateIntegral);
        return CommonResult.success(infoMap);
    }

    //检查用户的积分
    @ResponseBody
    @PostMapping(value = "/checkIntegral")
    public Object checkIntegral(@RequestParam @NotBlank(message = "用户账号不能为空") String customerNumber
                ,@RequestParam @NotNull Integer status){
        boolean result = customerService.checkIntegral(customerNumber, status);
        return CommonResult.success(result);
    }

    /**
     *
     * @param commodity 商品信息(随意)
     * @param customernumber 用户账号
     * @param status 状态,1顶价成功,2用户违约
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/onOrder")
    public Object onOrder(@RequestParam(required = false) String commodity,
                        @RequestParam @NotBlank(message = "用户账号不能为空!") String customernumber,
                          @RequestParam @NotNull(message = "订单状态不能为空!")Integer status){
        customerService.addOrder(commodity,customernumber,status);
        return CommonResult.success();
    }

    //根据用户号获取用户信息
    @ResponseBody
    @PostMapping(value = "/customerInfo")
    public Object customerInfo(@RequestParam @NotBlank(message = "用户账号不能为空!") String customernumber){
        SysCustomer customerInfo = customerService.getByCustomernumber(customernumber);
        if(customerInfo == null){
            return CommonResult.failed();
        }
        return CommonResult.success(customerInfo);
    }

    //删除一条用户信息
    @ResponseBody
    @PostMapping(value = "/del")
    public Object del(@RequestParam @NotNull(message = "用户id不能为空!") Integer customerId){
        boolean result = customerService.removeById(customerId);
        if(result){
            return CommonResult.success();
        }
        return CommonResult.failed("删除失败,请稍后重试!");
    }

    @ResponseBody
    @PostMapping(value = "/saveAdd")
    public Object saveAdd(@RequestBody SysCustomer newCustomer){

        boolean save = customerService.save(newCustomer);
        if(save){
            return CommonResult.success("保存用户失败,请稍后重试!");
        }
        return CommonResult.failed();
    }

    @ResponseBody
    @PostMapping(value = "/list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       @RequestParam(value = "customerName",required = false) String customerName,
                       @RequestParam(value = "rangeIntegral",required = false) String rangeIntegral,
                       @RequestParam(value = "startTime",required = false) String startTime,
                       @RequestParam(value = "endTime",required = false) String endTime) {
        //查询数据库中的数据
        List<SysCustomer> customerList = customerService.getCustomerList(page,limit,customerName,rangeIntegral,startTime,endTime);
        //返回给前端的对象
        PageData<SysCustomer> pageData = new PageData<>(customerList);
        return pageData;
    }

    @GetMapping(value = "/add")
    public String add(){
        return "customer/add";
    }

    @GetMapping
    public String page(){
          return "customer/list";
    }

}

