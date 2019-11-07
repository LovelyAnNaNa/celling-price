package com.whtt.cellingprice.controller;


import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.entity.pojo.SysAccount;
import com.whtt.cellingprice.service.SysAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 分页搜索查询
     * @param page
     * @param size
     * @param status
     * @param keyword
     * @return
     */
    public PageData listByPageAndSearch(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size, Integer status, String keyword) {
        return sysAccountService.listByPageAndSearch(page, size, status, keyword);
    }

    /**
     * 查询账号登录信息
     * @param id
     * @return
     */
    public CommonResult getAccountLoginInfo(@NotNull(message = "请选择账号") Integer id) {
        return sysAccountService.getAccountLoginInfo(id);
    }

    /**
     * 新增账号
      * @return
     */
    @PostMapping("/insert")
    public CommonResult insert(@RequestBody SysAccount sysAccount) {
        return sysAccountService.insert(sysAccount);
    }

    /**
     * 修改
     * @param sysAccount
     * @return
     */
    @PostMapping("/update")
    public CommonResult update(@RequestBody SysAccount sysAccount) {
        return sysAccountService.update(sysAccount);
    }

    /**
     * 登录账号
     * @param id
     * @param loginInfo
     * @return
     */
    @PostMapping("/login")
    public CommonResult login(@NotNull(message = "请选择账号") Integer id,
                              @NotNull(message = "用户登录失败") String loginInfo) {
        return sysAccountService.login(id, loginInfo);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public CommonResult delete(@NotNull(message = "请选择账号") Integer id) {
        return sysAccountService.delete(id);
    }

    /**
     * 批量根据id删除
     * @param sysAccountList
     * @return
     */
    @PostMapping("/deleteSome")
    public CommonResult deleteSome(@RequestBody @NotNull(message = "请选择账号") List<SysAccount> sysAccountList) {
        return sysAccountService.deleteSome(sysAccountList);
    }
}

