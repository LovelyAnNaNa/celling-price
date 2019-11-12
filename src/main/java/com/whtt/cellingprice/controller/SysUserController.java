package com.whtt.cellingprice.controller;


import cn.hutool.core.date.DateUtil;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.entity.pojo.SysUser;
import com.whtt.cellingprice.service.SysOrderService;
import com.whtt.cellingprice.service.SysRechargeService;
import com.whtt.cellingprice.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>
 * 管理员用户表 前端控制器
 * </p>
 *
 * @author shj
 * @since 2019-11-07
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysOrderService orderService;
    @Autowired
    private SysRechargeService rechargeService;

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public Object login(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpSession session) throws IOException {
        SysUser userInfo = sysUserService.getByUsernameAndPassword(username, password);
        if(userInfo == null){
            return CommonResult.failed("用户名或密码不正确!");
        }
        session.setAttribute("user_id", userInfo.getId());
        return CommonResult.success();
    }

    @ResponseBody
    @RequestMapping(value = "/changePassword")
    public Object changePassword(@RequestParam(value = "pass", required = true) String pass, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("user_id");
        if (sysUserService.changePassword(pass, userId) >= 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed("更改密码失败");
        }
    }

    @GetMapping(value = "/index")
    public String index() {
        return "/index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "/login";
    }

    @GetMapping(value = "/forgetPassword")
    public String forgetPassword() {
        return "/forget-password";
    }

    @GetMapping(value = "/home")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        String today = DateUtil.today();
        //获取订单总交易总量
        int totalOrderCount = orderService.getOrderCount(null, null);
        //获取顶价成功订单和用户违约订单数量
        int successOrderCount = orderService.getOrderCount(1, null);
        int violateOrderCount = orderService.getOrderCount(2, null);
        //获取积分交易总额
        int totalDeductIntegral = orderService.getSumDeductintegral(null, null);
        //获取用户充值的总积分
        int totalSumIntegral = rechargeService.getSumIntegral(null, null);

        //获取今日订单交易总量
        int todayOrderCount = orderService.getOrderCount(null, today);
        //获取今日顶价成功和用户违约订单数量
        int todaySuccessOrderCount = orderService.getOrderCount(1, today);
        int todayViolateOrderCount = orderService.getOrderCount(2, today);
        //获取今日订单的交易总额
        int todayDeductIntegral = orderService.getSumDeductintegral(null, today);
        //获取用户今日充值的总积分
        int todaySumIntegral = rechargeService.getSumIntegral(null, today);


        mav.addObject("totalOrderCount", totalOrderCount);
        mav.addObject("successOrderCount", successOrderCount);
        mav.addObject("violateOrderCount", violateOrderCount);
        mav.addObject("totalDeductIntegral", totalDeductIntegral);
        mav.addObject("totalSumIntegral", totalSumIntegral);
        mav.addObject("todayOrderCount", todayOrderCount);
        mav.addObject("todaySuccessOrderCount", todaySuccessOrderCount);
        mav.addObject("todayViolateOrderCount", todayViolateOrderCount);
        mav.addObject("todayDeductIntegral", todayDeductIntegral);
        mav.addObject("todaySumIntegral", todaySumIntegral);
        return mav;
    }

    @GetMapping(value = "/list")
    public String userManager() {
        return "UserManager/list";
    }
}

