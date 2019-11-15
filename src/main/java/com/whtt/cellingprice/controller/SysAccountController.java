package com.whtt.cellingprice.controller;

import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.entity.pojo.SysAccount;
import com.whtt.cellingprice.service.SysAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weishilei
 * @since 2019-11-06
 */
@Validated
@Controller
@RequestMapping("/sysAccount")
public class SysAccountController {

    @Autowired
    private SysAccountService sysAccountService;

    /**
     * 展现账号列表
     * @return
     */
    @GetMapping("/showList")
    public String showList() {
        return "account/list";
    }


    /**
     * 展现新增账号
     * @return
     */
    @GetMapping("/add")
    public Object add(String phone, Integer id,ModelAndView model) {
        model.addObject("phone",phone);
        model.addObject("id",id);
        model.setViewName("account/add");
        return model;
    }

    /**
     * 分页搜索查询
     * @param page
     * @param limit
     * @param status
     * @param keyword
     * @return
     */
    @ResponseBody
    @GetMapping("/listByPageAndSearch")
    public PageData listByPageAndSearch(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                        Integer status, String keyword) {
        return sysAccountService.listByPageAndSearch(page, limit, status, keyword);
    }

    /**
     * 获取账号验证码
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/getAccountCode")
    public CommonResult getAccountCode(@NotNull(message = "请选择账号") Integer id) {
        return sysAccountService.getAccountCode(id);
    }

    /**
     * 获取账号验证码
     * @param phone
     * @return
     */
    @ResponseBody
    @GetMapping("/getAccountCodeByPhone")
    public CommonResult getAccountCodeByPhone(@NotNull(message = "请输入正确手机号") String phone) {
        return sysAccountService.getAccountCode(phone);
    }

    /**
     * 获取账号登录信息
     * @param id
     * @param code
     * @return
     */
    @ResponseBody
    @GetMapping("/getAccountLoginInfo")
    public CommonResult getAccountLoginInfo(@NotNull(message = "请选择账号") Integer id, String code) {
        return sysAccountService.getAccountLoginInfo(id, code);
    }

    /**
     * 出价
     * @param url
     * @return
     */
    @ResponseBody
    @PostMapping("/offer")
    public CommonResult offer(@NotBlank(message = "请选择正确拍品") String url,
                              @NotBlank(message = "用户账号为空") String customerNumber,
                              @NotNull(message = "请选择正确类型") Integer type) {
        return sysAccountService.offer(url, customerNumber, type);
    }

    /**
     * 新增账号
      * @return
     */
    @ResponseBody
    @PostMapping("/insert")
    public CommonResult insert(@RequestBody SysAccount sysAccount) {
        return sysAccountService.insert(sysAccount);
    }

    /**
     * 修改
     * @param sysAccount
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public CommonResult update(@RequestBody SysAccount sysAccount) {
        return sysAccountService.update(sysAccount);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult delete(@NotNull(message = "请选择账号") Integer id) {
        return sysAccountService.delete(id);
    }

    /**
     * 批量根据id删除
     * @param sysAccountList
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteSome")
    public CommonResult deleteSome(@RequestBody @NotNull(message = "请选择账号") List<SysAccount> sysAccountList) {
        return sysAccountService.deleteSome(sysAccountList);
    }
}

