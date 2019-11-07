package com.whtt.cellingprice.service;

import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author wbh
 * @since 2019-11-06
 */
public interface SysCustomerService extends IService<SysCustomer> {

    List<SysCustomer> getCustomerList(Integer page,Integer limit,String customerName,String rangeIntegral,String startTime,String endTime);

    //根据用户账号获取用户的剩余积分
    SysCustomer getByCustomernumber(String customerNumber);

    //判断用户积分余额是否够用,true不够,false够
    boolean checkIntegral(String customerNumber,Integer status);

    void addOrder(String commodity,String customerNumber,Integer status);

    //根据用户名模糊查询
    List<SysCustomer> getByFuzzyCustomername(String customerName);
}
