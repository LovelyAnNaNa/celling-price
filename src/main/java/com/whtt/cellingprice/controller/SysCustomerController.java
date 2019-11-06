package com.whtt.cellingprice.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.whtt.cellingprice.service.SysCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @PostMapping(value = "/list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        PageHelper.startPage(page,limit);
        //查询数据库中的数据
        List<SysCustomer> customerList = customerService.list(null);
//        PageInfo<SysCustomer> pageInfo = new PageInfo<>(customerList);
        //返回给前端的对象
        PageData<SysCustomer> pageData = new PageData<>(customerList);
        return pageData;
    }


    @GetMapping
    public String page(){
          return "customer/list";
    }

}

