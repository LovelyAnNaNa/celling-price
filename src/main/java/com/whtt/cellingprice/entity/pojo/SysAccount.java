package com.whtt.cellingprice.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author weishilei
 * @since 2019-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_account")
public class SysAccount extends Model<SysAccount> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 账号状态,0未登录,1已登录,3失效,4次数使用完
     */
    private Integer status;

    /**
     * 登录信息
     */
    private String loginInfo;

    /**
     * 账号消息
     */
    private String msg;

    /**
     * 使用次数
     */
    private Integer count;

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


    public static final String ID = "id";

    public static final String PHONE = "phone";

    public static final String STATUS = "status";

    public static final String LOGIN_INTO = "login_into";

    public static final String CREATE_ID = "create_id";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_ID = "update_id";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
