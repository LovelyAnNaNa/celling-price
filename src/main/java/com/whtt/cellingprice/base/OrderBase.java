package com.whtt.cellingprice.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: wbh
 * @Date: 2019/11/7 13:26
 * @Description:
 */
@Getter
@Setter
public class OrderBase<T> extends Model {

    //下订单的用户信息
    @TableField(exist =  false)
    private SysCustomer customer;
}
