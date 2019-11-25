package com.whtt.cellingprice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.Constant;
import com.whtt.cellingprice.common.PageData;
import com.whtt.cellingprice.config.DataConfig;
import com.whtt.cellingprice.entity.pojo.SysAccount;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.whtt.cellingprice.mapper.SysAccountMapper;
import com.whtt.cellingprice.service.SysAccountService;
import com.whtt.cellingprice.service.SysCustomerService;
import com.whtt.cellingprice.service.SysOrderService;
import com.whtt.cellingprice.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;

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
        List<SysAccount> accountList = accountMapper.selectList(queryWrapper.orderByDesc("id"));
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
        String messgae = getUserInfo(account, phone, code);
        if ("success".equals(messgae)) {
            return CommonResult.success();
        }

        return CommonResult.failed(messgae);
    }

    /**
     * 出价
     *
     * @param url
     * @return
     */
    @Override
    public CommonResult offer(String url, String customerNumber, Integer type) {
        if (1 != type && 2 != type) {
            return CommonResult.failed("请选择正确类型");
        }
        String typeName = 1 == type ? "顶价" : "违约";

        QueryWrapper<SysCustomer> queryWrapper = new QueryWrapper<SysCustomer>().eq("customer_number", customerNumber);
        SysCustomer customer = sysCustomerService.getOne(queryWrapper);
        if (null == customer) {
            return CommonResult.failed("请先开通账号");
        }
        boolean flag = sysCustomerService.checkIntegral(customerNumber, type);
        if (flag) {
            return CommonResult.failed(typeName + "所需积分：" + DataConfig.getDeductIntegral(type) + "。你当前积分为：" + customer.getIntegral() + "，请充值");
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
        Map<String, String> result = getOfferParamter(goodsId);
        String offerParamter = result.get("paramter");
        if (StringUtils.isBlank(offerParamter)) {
            return CommonResult.failed("请选择正确拍品");
        }
        String replay = result.get("replay");

        flag = false;
        //查询可用账号
        QueryWrapper<SysAccount> query = new QueryWrapper<>();
        query.eq("status", Constant.ACCOUNT_STATUS_LOGIN);
        query.gt("count", 0);

        String msg = "";
        Integer integral = customer.getIntegral();
        List<SysAccount> accountList = baseMapper.selectList(query);
        for (SysAccount account : accountList) {
            String loginInfo = account.getLoginInfo();
            JSONObject jsonObject = JSONObject.parseObject(loginInfo);
            String uri = jsonObject.getString("uri");
            offerParamter += uri;

            Map<String, String> headres = new HashMap<>();
            headres.put("Accept", "application/json");
            headres.put("Origin", "https://w.weipaitang.com");
            headres.put("User-Agent", String.format(Constant.UA, randomDeviceId()));
            headres.put("Cookie", "userinfo_cookie=" + jsonObject.getString("cookie"));

            String response = RequestUtil.sendGet(Constant.URL_OFFER, offerParamter, headres);
            JSONObject jo = JSONObject.parseObject(response);
            Integer code = jo.getInteger("code");
            //0：成功，420：拍价已领先
            if (0 != code && 420 != code) {
                msg = jo.getString("msg");
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
            replay += "拍价账号：" + account.getPhone() + "\n";

            flag = true;
            break;
        }

        if (flag) {
            Integer deductIntegral = DataConfig.getDeductIntegral(type);
            int newIntegral = integral - deductIntegral;
            newIntegral = newIntegral < 0 ? 0 : newIntegral;
            customer.setIntegral(newIntegral);
            customer.updateById();
            replay += "花费积分：" + deductIntegral + "\n剩余积分：" + newIntegral + "\n操作状态：成功";
            sysCustomerService.addOrder(url, customerNumber, type);

            return CommonResult.success(replay);
        }

        replay += "剩余积分：" + integral + "操作状态：失败\n失败信息：" + msg;
        return CommonResult.failed(replay);
    }

    /**
     * 批量添加
     *
     * @param count
     * @return
     */
    @Override
    public CommonResult addSome(Integer count) {
        if (count <= 0 || count > 50) {
            return CommonResult.failed("添加数量1-50之间");
        }
        String username = DataConfig.laixinUsername;
        String password =DataConfig.laixinPassword;
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return CommonResult.failed("请在系统配置正确配置来信账号密码");
        }

        String response = RequestUtil.sendGet(Constant.LAIXIN_LOGIN_URL,
                "action=loginIn&name=" + username + "&password=" + password, new HashMap<>());
        if (StringUtils.isBlank(response)) {
            return CommonResult.failed("请在系统配置正确配置来信账号密码");
        }
        String[] responseArray = response.split("\\|");
        String code = responseArray[0];
        String token = responseArray[responseArray.length - 1];
        if (!"1".equals(code)) {
            return CommonResult.failed(token);
        }

        response = RequestUtil.sendGet(Constant.LAIXIN_LOGIN_URL,
                "action=getPhone&sid=" + DataConfig.laixinId + "&token=" + token + "&size=" + count, new HashMap<>());
        responseArray = response.split("\\|");
        code = responseArray[0];
        String phone = responseArray[responseArray.length - 1];
        if (!"1".equals(code)) {
            return CommonResult.failed(phone);
        }

        String[] phoneArray = phone.split(",");
        phoneSomeLogin(phoneArray, token);
        return CommonResult.success();
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
    private Map<String, String> getOfferParamter(String goodsId) {
        String paramter;
        String replay;
        Map<String, String> result = new HashMap<>();

        String response = RequestUtil.sendGet(Constant.URL_GET_GOODS_INFO, "saleUri=" + goodsId, Constant.URL_OFFER_HEADERS);
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(response);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject shop = data.getJSONObject("shop");
            //店铺名称
            String nickname = shop.getString("nickname");

            JSONObject sale = data.getJSONObject("sale");
            //拍品名称
            String title = sale.getString("title");

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
            replay = "店铺名称：" + nickname + "\n拍品名称：" + title + "\n拍价金额：" + bidPrice + "\n";
            result.put("paramter", paramter);
            result.put("replay", replay);
        } catch (Exception e) {}

        return result;
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
        Constant.URL_SEND_CODE_HEADERS.put("User-Agent", String.format(Constant.UA, randomDeviceId()));
        String response = RequestUtil.sendGet(Constant.URL_SEND_CODE, "type=sms&telephone=" + phone + "&nationCode=86", Constant.URL_SEND_CODE_HEADERS);
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(response);
            if (getResponseCode(jsonObject) || "操作失败".equals(jsonObject.getString("msg"))) {
                return CommonResult.failed(jsonObject.getString("msg"));
            }

        } catch (Exception e) {
            return CommonResult.failed();
        }

        return CommonResult.success();
    }

    /**
     * 获取用户信息
     * @param account
     * @param phone
     * @param code
     * @return
     */
    private String getUserInfo(SysAccount account, String phone, String code) {
        JSONObject deviceInfo = new JSONObject();
        deviceInfo.put("channel", "oppo");
        deviceInfo.put("deviceId", randomDeviceId());
        deviceInfo.put("os", "android");
        deviceInfo.put("appVersion", "3.5.1");

        try {
            Constant.URL_SEND_CODE_HEADERS.put("User-Agent", String.format(Constant.UA, randomDeviceId()));
            String response = RequestUtil.sendGet(Constant.URL_GET_ACCOUNT_INFO,
                    "type=0&telephone=" + phone + "&verifyCode=" + code + "&nationCode=86&deviceInfo=" + URLEncoder.encode(deviceInfo.toJSONString(), "utf-8"), Constant.URL_SEND_CODE_HEADERS);
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (getResponseCode(jsonObject)) {
                return jsonObject.getString("msg");
            }

            JSONObject data = jsonObject.getJSONObject("data");
            account.setPhone(phone);
            account.setLoginInfo(data.toJSONString());
            account.setStatus(Constant.ACCOUNT_STATUS_LOGIN);
            account.setMsg("登录成功");
            account.insertOrUpdate();
        } catch (Exception e) {
            return "fail";
        }

        return "success";
    }

    /**
     * 手机号批量登录
     * @param phoneArray
     */
    private void phoneSomeLogin(String[] phoneArray, String token) {
        System.out.println(Arrays.toString(phoneArray));
        for (String phone : phoneArray) {
            new Thread(() -> {
                String phoneCode = "";
                SysAccount account = new SysAccount();
                CommonResult resp = getCode(phone);
                long code = resp.getCode();
                if (200 == code) {
                    for (int i = 0; i < 20; i++) {
                        String response = RequestUtil.sendGet(Constant.LAIXIN_LOGIN_URL,
                                "action=getMessage&sid=" + DataConfig.laixinId + "&phone=" + phone + "&token=" + token, new HashMap<>());
                        String[] responseArray = response.split("\\|");
                        String respCode = responseArray[0];
                        if ("0".equals(respCode)) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {}
                            continue;
                        }

                        phoneCode = responseArray[responseArray.length - 1];
                        break;
                    }
                }
                if (StringUtils.isNotBlank(phoneCode) && phoneCode.contains("您的验证码是：")) {
                    int index = phoneCode.lastIndexOf("您的验证码是：");
                    phoneCode = phoneCode.substring(index + "您的验证码是：".length(), phoneCode.indexOf("，")).trim();
                    System.out.println("login success [+] " + phone);
                    getUserInfo(account, phone, phoneCode);
                } else {
                    System.out.println("login fail [-] " + phone);
                    // 加入黑名单
                    RequestUtil.sendGet(Constant.LAIXIN_LOGIN_URL,
                            "action=addBlacklist&sid=" + DataConfig.laixinId + "&phone=" + phone + "&token=" + token, new HashMap<>());
                }
            }).start();
        }
    }

    /**
     * 随机获取设备
     * @return
     */
    private String randomDeviceId() {
        Random random = new Random();
        StringBuilder deviceId = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            deviceId.append(random.nextInt(10));
        }

        return deviceId.toString();
    }
}