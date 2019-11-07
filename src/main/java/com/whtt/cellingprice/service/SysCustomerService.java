package com.whtt.cellingprice.service;

import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author wbh
 * @since 2019-11-06
 */
public interface SysCustomerService extends IService<SysCustomer> {

    //根据用户账号获取用户的剩余积分
    SysCustomer getByCustomernumber(String customerNumber);

    void addOrder(String commodity,String customerNumber);
}
