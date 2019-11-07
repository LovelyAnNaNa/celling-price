package com.whtt.cellingprice.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author wbh
 * @since 2019-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_customer")
public class SysCustomer extends Model<SysCustomer> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String customerName;

    /**
     * 用户账号
     */
    private String customerNumber;

    /**
     * 用户积分
     */
    private Integer integral;

    /**
     * 创建用户id
     */
    private Integer createId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更改用户id
     */
    private Integer updateId;

    /**
     * 最后一次更改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 预留字段
     */
    private String standbyApplication;


    public static final String ID = "id";

    public static final String CUSTOMER_NAME = "customer_name";

    public static final String CUSTOMER_NUMBER = "customer_number";

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
