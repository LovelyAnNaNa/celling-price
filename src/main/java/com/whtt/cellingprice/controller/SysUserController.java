package com.whtt.cellingprice.controller;


import com.whtt.cellingprice.entity.pojo.SysUser;
import com.whtt.cellingprice.service.SysUserService;
import com.whtt.cellingprice.tool.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @ResponseBody
    @RequestMapping(value = "/login")
    public  Object login(HttpServletRequest request){
        String username= request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println(username);
        if (sysUserService.selectLogin(username,password).equals(null)){
                return CommonResult.failed("用户名或密码输入有误");
        }else {
            return CommonResult.success("登录成功");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/changePassword")
    public  Object changePassword(HttpServletRequest request){
        String username= request.getParameter("username");
        String password=request.getParameter("password");
        if (sysUserService.changePassword(username,password)!=0){
            return CommonResult.success("更改密码成功");
        }else {
            return CommonResult.failed("更改密码失败");
        }
    }
    @GetMapping(value = "/login")
    public String login(){
        return "/jurisdiction_login";
    }
    @GetMapping(value = "/forget-password")
    public String forgetPassword(){
        return "/forget-password";
    }
}

