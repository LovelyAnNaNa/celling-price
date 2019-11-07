package com.whtt.cellingprice.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 魔法值
 * @author weishilei
 * @date 2019.11.06
 */
public class Constant {

    /**
     * 账号未登录
     */
    public final static Integer ACCOUNT_STATUS_NOT_LOGIN = 0;

    /**
     * 账号已登录
     */
    public final static Integer ACCOUNT_STATUS_LOGIN = 1;

    /**
     * 账号失效
     */
    public final static Integer ACCOUNT_STATUS_FAILURE = 2;


    /**
     * 产品id长度
     */
    public final static Integer GOODS_ID_LENGTH = 16;

    /**
     * 发送验证码url
     */
    public final static String URL_SEND_CODE = "https://api.weipaitang.com/app/v1.0/user/send-code";

    /**
     * 获取账号登录信息url
     */
    public final static String URL_GET_ACCOUNT_INFO = "https://api.weipaitang.com/app/v1.0/user/login";

    /**
     * 获取商品信息url
     */
    public final static String URL_GET_GOODS_INFO = "https://api.weipaitang.com/wechat/v1.0/sale/mini-detail";

    /**
     * 出价url
     */
    public final static String URL_OFFER = "https://api.weipaitang.com/wechat/v1.0/bid/to-bid-l";

    /**
     * 发送验证码请求他
     */
    public final static Map<String, String> URL_SEND_CODE_HEADERS = new HashMap<>();

    static {
        URL_SEND_CODE_HEADERS.put("sec-fetch-mode", "cors");
        URL_SEND_CODE_HEADERS.put("sec-fetch-site", "same-site");
        URL_SEND_CODE_HEADERS.put("origin", "https://w.weipaitang.com");
        URL_SEND_CODE_HEADERS.put("user-agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Mobile Safari/537.36");
        URL_SEND_CODE_HEADERS.put("referer", "https://w.weipaitang.com/my/index/buyer?r=menu_my&c=menu_my");
    }
}
