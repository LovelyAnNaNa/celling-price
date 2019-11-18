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
     * 次数使用完
     */
    public final static Integer ACCOUNT_STATUS_COUNT = 3;


    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

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
     * 来信url
     */
    public final static String LAIXIN_LOGIN_URL = "http://api.smskkk.com/api/do.php";

    /**
     * 发送验证码请求头
     */
    public final static Map<String, String> URL_SEND_CODE_HEADERS = new HashMap<>();

    /**
     * 顶价请求头
     */
    public final static Map<String, String> URL_OFFER_HEADERS = new HashMap<>();

    public final static Map<String, String> SEND_URL_OFFER_HEADERS = new HashMap<>();

    static {
        URL_SEND_CODE_HEADERS.put("sec-fetch-mode", "cors");
        URL_SEND_CODE_HEADERS.put("sec-fetch-site", "same-site");
        URL_SEND_CODE_HEADERS.put("referer", "app.weipaitang.com");
        URL_SEND_CODE_HEADERS.put("origin", "app.weipaitang.com");
        URL_SEND_CODE_HEADERS.put("user-agent", "NetType/NETWORK_WIFI Language/zh_CN WptMessenger/3.5.0 Channel/xiaomi wptAid/xiaomi DeviceId/861742042644141 os/android oVersion/9 cVersion/3.5.0 ua/RedmiNote5 identity/e6a0ef5131a28a25a16e556c75eb1d80 brand/xiaomi secretKey/secretKey");
        URL_SEND_CODE_HEADERS.put("Cookie", "wptSceneChannel=xiaomi; wptAid=xiaomi");

        URL_OFFER_HEADERS.put("sec-fetch-mode", "cors");
        URL_OFFER_HEADERS.put("sec-fetch-site", "same-site");
        URL_OFFER_HEADERS.put("origin", "https://w.weipaitang.com");
        URL_OFFER_HEADERS.put("user-agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Mobile Safari/537.36");

        SEND_URL_OFFER_HEADERS.put("User-Agent", "Mozilla/5.0 (Linux; Android 9; Redmi Note 5 Build/PKQ1.180904.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/66.0.3359.126 MQQBrowser/6.2 TBS/045008 Mobile Safari/537.36 NetType/NETWORK_WIFI Language/zh_CN WptMessenger/3.5.0 Channel/xiaomi wptAid/xiaomi DeviceId/861742042644141 identity/e6a0ef5131a28a25a16e556c75eb1d80 brand/xiaomi model/RedmiNote5");
        SEND_URL_OFFER_HEADERS.put("Cookie", "wptSceneChannel=xiaomi; wptAid=xiaomi");
        SEND_URL_OFFER_HEADERS.put("origin", "https://w.weipaitang.com");
    }
}
