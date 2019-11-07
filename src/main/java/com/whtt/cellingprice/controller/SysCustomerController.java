package com.whtt.cellingprice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.whtt.cellingprice.service.SysCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
                       @RequestParam(value = "rangeIntegral",required = false) String rangeIntegral) {
        PageHelper.startPage(page,limit);
        QueryWrapper<SysCustomer> customerQueryWrapper = new QueryWrapper<>();
        //用户名模糊查询
        if(StringUtils.isNotBlank(customerName)){
            customerQueryWrapper.like("customer_name",customerName);
        }
        //价格区间
        if(StringUtils.isNotBlank(rangeIntegral)){
            String[] range = rangeIntegral.split("-");
            if(range.length == 2){
                customerQueryWrapper.between("integral",range[0],range[1]);
            }
        }
        //查询数据库中的数据
        List<SysCustomer> customerList = customerService.list(customerQueryWrapper);
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

