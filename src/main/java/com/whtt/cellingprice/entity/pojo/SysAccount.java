package com.whtt.cellingprice.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wbh
 * @since 2019-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_account")
public class SysAccount extends Model<SysAccount> {

    private static final long serialVersionUID=1L;

    /**
     * 账号id
     */
    private Integer id;

    /**
     * 账号(对应微信账号)
     */
    private String accountWxNumber;

    /**
     * 账号状态,0未登录,1已登录
     */
    private Integer status;

    /**
     * 登录后的cookie
     */
    private String cookie;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 创建账号的用户id
     */
    private Integer createId;

    private LocalDateTime createTime;

    /**
     * 最后一次修改的用户
     */
    private Integer updateId;

    /**
     * 最后一次修改的时间
     */
    private LocalDateTime updateTime;

    /**
     * 预留字段1
     */
    private String standbyApplication;


    public static final String ID = "id";

    public static final String ACCOUNT_WX_NUMBER = "account_wx_number";

    public static final String STATUS = "status";

    public static final String COOKIE = "cookie";

    public static final String USERNAME = "username";

    public static final String INTEGRAL = "integral";

    public static final String CREATE_ID = "create_id";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_ID = "update_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String STANDBY_APPLICATION = "standby_application";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
