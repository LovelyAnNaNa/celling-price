package com.whtt.cellingprice.controller;


import com.github.pagehelper.PageHelper;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.entity.pojo.SysOrder;
import com.whtt.cellingprice.service.SysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
@RequestMapping("/admin/sysOrder")
public class SysOrderController {

    @Autowired
    private SysOrderService orderService;

    @ResponseBody
    @PostMapping(value = "/list")
    public Object list(@RequestParam(value = "page",defaultValue = "1")Integer page,@RequestParam(value = "limit",defaultValue = "10")Integer limit,
                       @RequestParam(value = "customerName",required = false)String customerName,
                       @RequestParam(value = "rangeIntegral",required = false)String rangeIntegral,
                       @RequestParam(value = "status",required = false) Integer status){
        List<SysOrder> orderList = orderService.getOrderList(page, limit, customerName, rangeIntegral,status);

        return new PageData<SysOrder>(orderList);
    }

    @ResponseBody
    @PostMapping(value = "/del")
    public Object del(@RequestParam @NotNull(message = "订单id不能为空!") Integer orderId){
        orderService.removeById(orderId);
        return CommonResult.success();
    }

    @GetMapping
    public String listPage(){
          return "order/list";
    }
}

