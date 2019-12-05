package com.whtt.cellingprice.service;

import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.entity.pojo.SysAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author weishilei
 * @since 2019-11-06
 */
public interface SysAccountService extends IService<SysAccount> {

    /**
     * 新增账号
     * @param sysAccount
     * @return
     */
    CommonResult insert(SysAccount sysAccount);

    /**
     * 登录账号
     * @param id
     * @param loginInfo
     * @return
     */
    CommonResult login(Integer id, String loginInfo);

    /**
     * 根据id删除账号
     * @param id
     * @return
     */
    CommonResult delete(Integer id);

    /**
     * 批量根据id删除账号
     * @param accountList
     * @return
     */
    CommonResult deleteSome(List<SysAccount> accountList);

    /**
     * 分页搜索查询
     * @param page
     * @param size
     * @param status
     * @param keyword
     * @return
     */
    PageData listByPageAndSearch(Integer page, Integer size, Integer status, String keyword, Integer type);

    /**
     * 修改
     * @param sysAccount
     * @return
     */
    CommonResult update(SysAccount sysAccount);

    /**
     * 获取账号验证码
     * @param id
     * @return
     */
    CommonResult getAccountCode(Integer id);

    /**
     * 根据手机号获取验证码
     * @param phone
     * @return
     */
    CommonResult getAccountCode(String phone);

    /**
     * 获取账号信息
     * @param id
     * @param code
     * @return
     */
    CommonResult getAccountLoginInfo(Integer id, String code);

    /**
     * 出价
     * @param url
     * @param customerNumber
     * @param type
     * @return
     */
    CommonResult offer(String url, String customerNumber, Integer type);

    /**
     * 批量添加
     * @param count
     * @return
     */
    CommonResult addSome(Integer count);

    /**
     * 增加账号
     * @param data
     * @param phone
     * @return
     */
    CommonResult add(String data, String phone, Integer type);
}
