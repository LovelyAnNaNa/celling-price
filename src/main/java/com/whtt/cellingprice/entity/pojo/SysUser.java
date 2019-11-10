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
 * 管理员用户表
 * </p>
 *
 * @author shj
 * @since 2019-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID=1L;

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 用户头像
     */
    private String icon;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 是否锁定 0:不锁 ,1:锁
     */
    private Integer locked;

    /**
     * 是否为管理员,0不是,1是
     */
    private Integer isAdmin;

    private LocalDateTime createTime;

    /**
     * 创建用户
     */
    private Integer createId;

    private LocalDateTime updateTime;

    /**
     * 最后一次修改的用户id
     */
    private Integer updateId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 预留字段
     */
    private String standbyApplication;


    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String NICK_NAME = "nick_name";

    public static final String SALT = "salt";

    public static final String ICON = "icon";

    public static final String TEL = "tel";

    public static final String EMAIL = "email";

    public static final String LOCKED = "locked";

    public static final String IS_ADMIN = "is_admin";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_ID = "create_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_ID = "update_id";

    public static final String REMARK = "remark";

    public static final String STANDBY_APPLICATION = "standby_application";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
