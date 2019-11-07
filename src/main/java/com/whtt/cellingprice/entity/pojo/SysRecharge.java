package com.whtt.cellingprice.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.whtt.cellingprice.base.RechargeBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.*;
import javax.validation.constraints.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author wbh
 * @since 2019-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_recharge")
public class SysRecharge extends RechargeBase<SysRecharge> {

    private static final long serialVersionUID=1L;

    private Integer id;

    /**
     * 充值的用户d
     */
    @NotNull(message = "充值的用户id不能为空")
    private Integer customerId;

    /**
     * 充值的金额(单位:元)
     */
    private Integer money;

    /**
     * 充值的积分数
     */
    @NotNull(message = "积分改动金额不能为空")
    private Integer integral;

    /**
     * 添加记录的用户id
     */
    private Integer createId;

    /**
     * 充值记录创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更改信息的用户id
     */
    private Integer updateId;

    /**
     * 记录更改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 预留字段
     */
    private String standbyApplication;


    public static final String ID = "id";

    public static final String CUSTOMER_ID = "customer_id";

    public static final String MONEY = "money";

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

    public void validateParams() {
        //调用JSR303验证工具，校验参数
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<SysRecharge>> violations = validator.validate(this);
        Iterator<ConstraintViolation<SysRecharge>> iter = violations.iterator();
        if (iter.hasNext()) {
            //需要springmvc捕获全局异常
            throw new ConstraintViolationException(violations);
        }
    }

}
