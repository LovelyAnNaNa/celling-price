package com.whtt.cellingprice.controller;


import cn.hutool.core.date.DateUtil;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.service.SysOrderService;
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

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public Object login(@RequestParam(value = "username", required = false) @SessionAttribute(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpSession session) throws IOException {
        if (sysUserService.selectLogin(username, password).isEmpty()) {
            return CommonResult.failed("用户名或密码输入有误");
        } else {
            session.setAttribute("username", "username");
            return CommonResult.success("登录成功");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/changePassword")
    public Object changePassword(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password) {
        if (sysUserService.changePassword(username, password) != 0) {
            return CommonResult.success("更改密码成功");
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
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        //获取订单总交易数量和今日交易数量
        int totalOrderCount = orderService.getOrderCount(null, null);
        int todayOrderCount = orderService.getOrderCount(null, DateUtil.today());
        //获取顶价成功订单和用户违约订单数量
        int successOrderCount = orderService.getOrderCount(1, null);
        int violateOrderCount = orderService.getOrderCount(2, null);


        mav.addObject("totalOrderCount",totalOrderCount);
        mav.addObject("todayOrderCount",todayOrderCount);
        mav.addObject("successOrderCount",successOrderCount);
        mav.addObject("violateOrderCount",violateOrderCount);
        return mav;
    }

    @GetMapping(value = "/list")
    public String userManager() {
        return "UserManager/list";
    }
}

