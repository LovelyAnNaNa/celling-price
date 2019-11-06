package com.whtt.cellingprice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.Constant;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.entity.pojo.SysAccount;
import com.whtt.cellingprice.mapper.SysAccountMapper;
import com.whtt.cellingprice.service.SysAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.cellingprice.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author weishilei
 * @since 2019-11-06
 */
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements SysAccountService {

    /**
     * 新增账号
     *
     * @param sysAccount
     * @return
     */
    @Override
    public CommonResult insert(SysAccount sysAccount) {
        String phone = sysAccount.getPhone();
        SysAccount account = selectByPhone(phone);
        if (null != account) {
            return CommonResult.failed("手机号重复添加");
        }

        account.setStatus(Constant.ACCOUNT_STATUS_NOT_LOGIN);
        account.setCreateTime(LocalDateTime.now());
        boolean flag = account.insert();
        if (flag) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    /**
     * 登录账号
     *
     * @param id
     * @param loginInfo
     * @return
     */
    @Override
    public CommonResult login(Integer id, String loginInfo) {
        SysAccount account = baseMapper.selectById(id);
        if (null == account) {
            return CommonResult.failed("账号不存在");
        }

        account.setLoginInfo(loginInfo);
        account.setUpdateTime(LocalDateTime.now());
        account.setStatus(Constant.ACCOUNT_STATUS_LOGIN);
        boolean flag = account.updateById();
        if (flag) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    /**
     * 根据id删除账号
     *
     * @param id
     * @return
     */
    @Override
    public CommonResult delete(Integer id) {
        SysAccount account = baseMapper.selectById(id);
        if (null == account) {
            return CommonResult.failed("账号不存在");
        }

        boolean flag = account.deleteById();
        if (flag) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    /**
     * 批量根据id删除账号
     *
     * @param accountList
     * @return
     */
    @Override
    public CommonResult deleteSome(List<SysAccount> accountList) {
        for (SysAccount account : accountList) {
            delete((account.getId()));
        }

        return CommonResult.success();
    }

    /**
     * 分页搜索查询
     *
     * @param page
     * @param size
     * @param status
     * @param keyword
     * @return
     */
    @Override
    public PageData listByPageAndSearch(Integer page, Integer size, Integer status, String keyword) {
        QueryWrapper<SysAccount> queryWrapper = new QueryWrapper<>();
        if (null != status) {
            queryWrapper.eq("status", status).or();
        }
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.eq("phone", keyword);
        }

        IPage<SysAccount> iPage = page(new Page<>(page, size), queryWrapper);
        PageData<SysAccount> pageData = new PageData<>(iPage.getRecords());
        pageData.setCount(iPage.getTotal());

        return pageData;
    }

    /**
     * 修改
     *
     * @param sysAccount
     * @return
     */
    @Override
    public CommonResult update(SysAccount sysAccount) {
        String phone = sysAccount.getPhone();
        SysAccount account = selectByPhone(phone);
        if (null != account && !sysAccount.equals(account)) {
            return CommonResult.failed("手机号重复添加");
        }

        boolean flag = sysAccount.updateById();
        if (flag) {
            return CommonResult.success();
        }

        return CommonResult.failed();
    }

    /**
     * 获取用户登录信息
     *
     * @param id
     * @return
     */
    @Override
    public CommonResult getAccountLoginInfo(Integer id) {
        SysAccount account = baseMapper.selectById(id);
        if (null == account) {
            return CommonResult.failed("账号不存在");
        }
        String phone = account.getPhone();
        //请求获取验证码
        String response = RequestUtil.sendGet(Constant.URL_SEND_CODE, "type=sms&telephone=" + phone, Constant.URL_SEND_CODE_HEADERS);

        return null;
    }

    /**
     * 根据手机号查询
     * @param phone
     * @return
     */
    private SysAccount selectByPhone(String phone) {
        QueryWrapper<SysAccount> queryWrapper = new QueryWrapper<SysAccount>().eq("phone", phone);
        return baseMapper.selectOne(queryWrapper);
    }
}
