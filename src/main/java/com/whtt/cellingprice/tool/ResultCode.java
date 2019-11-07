package com.whtt.cellingprice.tool;


import com.baomidou.mybatisplus.extension.api.IErrorCode;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "Parameter check failed"),
    UNAUTHORIZED(401, "Token is not currently logged in or expired"),
    FORBIDDEN(403, "您没有该权限"),
    UNBOUNDED(600, "Wechat is not bound"),

    //1000~1500为用户方面异常
    LOGIN_FAILED(1000,"登录失败!"),
    USERNAME_NOT_FOUND(1001,"该用户名不存在!");

    private long code;
    private String msg;

    private ResultCode(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public long getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
