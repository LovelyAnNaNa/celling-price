package com.whtt.cellingprice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.Constant;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.entity.pojo.SysAccount;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.whtt.cellingprice.mapper.SysAccountMapper;
import com.whtt.cellingprice.service.SysAccountService;
import com.whtt.cellingprice.service.SysCustomerService;
import com.whtt.cellingprice.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    @Resource
    private SysAccountMapper accountMapper;
    @Autowired
    private SysCustomerService sysCustomerService;

    /**
     * 新增账号
     *
     * @param account
     * @return
     */
    @Override
    public CommonResult insert(SysAccount account) {
        String phone = account.getPhone();
        SysAccount temp = selectByPhone(phone);
        if (null != temp) {
            return CommonResult.failed("手机号重复添加");
        }

        account.setStatus(Constant.ACCOUNT_STATUS_NOT_LOGIN);
        account.setCreateTime(LocalDateTime.now());
        account.setMsg("未知");
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
            queryWrapper.like("status", status).or();
        }
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.like("phone", keyword).or().like("msg", keyword);
        }

        PageHelper.startPage(page,size);

        List<SysAccount> accountList = accountMapper.selectList(queryWrapper);
        PageData<SysAccount> pageData = new PageData<>(accountList);
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
     * 获取账号验证码
     *
     * @param id
     * @return
     */
    @Override
    public CommonResult getAccountCode(Integer id) {
        SysAccount account = baseMapper.selectById(id);
        if (null == account) {
            return CommonResult.failed("账号不存在");
        }
        String phone = account.getPhone();
        return getCode(phone);
    }

    /**
     * 根据手机号获取验证码
     *
     * @param phone
     * @return
     */
    @Override
    public CommonResult getAccountCode(String phone) {
        return getCode(phone);
    }

    /**
     * 获取账号信息
     *
     * @param id
     * @param code
     * @return
     */
    @Override
    public CommonResult getAccountLoginInfo(Integer id, String code) {
        SysAccount account = baseMapper.selectById(id);
        if (null == account) {
            return CommonResult.failed("账号不存在");
        }
        String phone = account.getPhone();
        String response = RequestUtil.sendGet(Constant.URL_GET_ACCOUNT_INFO, "type=2&telephone=" + phone + "&verifyCode=" + code + "&sc&wpjbPromoter", Constant.URL_SEND_CODE_HEADERS);

        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(response);
            if (getResponseCode(jsonObject)) {
                return CommonResult.failed(jsonObject.getString("msg"));
            }

            JSONObject data = jsonObject.getJSONObject("data");
            account.setLoginInfo(data.toJSONString());
            account.setStatus(Constant.ACCOUNT_STATUS_LOGIN);
            account.setMsg("登录成功");
            account.updateById();
        } catch (Exception e) {
            return CommonResult.failed();
        }

        return CommonResult.success();
    }

    /**
     * 出价
     *
     * @param url
     * @return
     */
    @Override
    public CommonResult offer(String url, String customerNumber, Integer type) {
        if (1 == type || 2 == type) {
            return CommonResult.failed("请选择正确类型");
        }
        QueryWrapper<SysCustomer> queryWrapper = new QueryWrapper<SysCustomer>().eq("customer_number", customerNumber);
        SysCustomer customer = sysCustomerService.getOne(queryWrapper);
        if (null == customer) {
            return CommonResult.failed("请先开通账号");
        }
        boolean flag = sysCustomerService.checkIntegral(customerNumber, type);
        if (flag) {
            return CommonResult.failed("当前积分为：" + customer.getIntegral() + "，请充值");
        }

        String goodsId;
        try {
            int index = url.lastIndexOf("/") + 1;
            int index2 = url.indexOf("?");
            goodsId = url.substring(index, index2);
            if (Constant.GOODS_ID_LENGTH != goodsId.length()) {
                return CommonResult.failed("请选择正确拍品");
            }
        } catch (Exception e) {
            return CommonResult.failed("请选择正确拍品");
        }
        //请求参数
        String offerParamter = getOfferParamter(url);
        if (StringUtils.isBlank(offerParamter)) {
            return CommonResult.failed("请选择正确拍品");
        }

        flag = false;
        //查询可用账号
        QueryWrapper<SysAccount> query = new QueryWrapper<>();
        query.eq("status", Constant.ACCOUNT_STATUS_LOGIN);
        query.gt("count", 0);

        List<SysAccount> accountList = baseMapper.selectList(query);
        for (SysAccount account : accountList) {
            String loginInfo = account.getLoginInfo();
            JSONObject jsonObject = JSONObject.parseObject(loginInfo);
            String uri = jsonObject.getString("uri");
            offerParamter += uri;

            String response = RequestUtil.sendGet(Constant.URL_OFFER, offerParamter, Constant.URL_SEND_CODE_HEADERS);
            JSONObject jo = JSONObject.parseObject(response);
            Integer code = jo.getInteger("code");
            if (0 != code) {
                String msg = jo.getString("msg");
                account.setMsg(msg);
                account.setStatus(Constant.ACCOUNT_STATUS_FAILURE);
                account.updateById();
                continue;
            }

            Integer count = account.getCount() - 1;
            if (0 >= count) {
                account.setStatus(Constant.ACCOUNT_STATUS_COUNT);
            }
            account.setCount(count);
            account.updateById();

            flag = true;
            break;
        }

        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    private boolean getResponseCode(JSONObject jsonObject) {
        Integer respCode = jsonObject.getInteger("code");
        if (0 != respCode) {
            return true;
        }

        return false;
    }

    /**
     * 获取出价请求接口参数
     * @param goodsId
     * @return
     */
    private String getOfferParamter(String goodsId) {
        String paramter = new String();
        String response = RequestUtil.sendGet(Constant.URL_GET_GOODS_INFO, "saleUri=" + goodsId, Constant.URL_SEND_CODE_HEADERS);
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(response);
            JSONObject sale = jsonObject.getJSONObject("data").getJSONObject("sale");
            JSONArray jsonArray = sale.getJSONObject("bid").getJSONArray("bidList");
            JSONObject priceJson = sale.getJSONObject("priceJson");
            int increase = priceJson.getInteger("increase");
            int lastBid = 0;
            int bidPrice = increase;
            if (jsonArray.size() > 0) {
                lastBid = jsonArray.getJSONObject(0).getInteger("price");
                bidPrice = lastBid + increase;
            }

            paramter = "saleUri=" + goodsId + "&bidPrice=" + bidPrice + "&lastBid=" + lastBid + "&__uuri=";
        } catch (Exception e) {}

        return paramter;
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

    /**
     * 获验证码
     * @param phone
     * @return
     */
    private CommonResult getCode(String phone) {
        String response = RequestUtil.sendGet(Constant.URL_SEND_CODE, "type=sms&telephone=" + phone, Constant.URL_SEND_CODE_HEADERS);
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(response);
            if (getResponseCode(jsonObject)) {
                return CommonResult.failed(jsonObject.getString("msg"));
            }

        } catch (Exception e) {
            return CommonResult.failed();
        }

        return CommonResult.success();

    }
}