package com.whtt.cellingprice.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: wbh
 * @Date: 2019/11/7 10:30
 * @Description:
 */
@Getter
@Setter
public class RechargeBase<T> extends Model {

    //充值的用户对象信息
    @TableField(exist = false)
    private SysCustomer customer;
}
