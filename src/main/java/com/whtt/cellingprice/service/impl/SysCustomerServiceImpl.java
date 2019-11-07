package com.whtt.cellingprice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whtt.cellingprice.config.DataConfig;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.whtt.cellingprice.entity.pojo.SysOrder;
import com.whtt.cellingprice.mapper.SysCustomerMapper;
import com.whtt.cellingprice.service.SysCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.cellingprice.service.SysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author wbh
 * @since 2019-11-06
 */
@Service
public class SysCustomerServiceImpl extends ServiceImpl<SysCustomerMapper, SysCustomer> implements SysCustomerService {

    @Autowired
    private SysOrderService orderService;
    @Resource
    private SysCustomerMapper customerMapper;

    @Override
    public SysCustomer getByCustomernumber(String customerNumber) {
        QueryWrapper<SysCustomer> customerQueryWrapper = new QueryWrapper<SysCustomer>().eq("customer_number", customerNumber);
        SysCustomer customerInfo = customerMapper.selectOne(customerQueryWrapper);
        return customerInfo;
    }

    @Override
    public void addOrder(String url, String customerNumber) {
        //获取用户信息
        SysCustomer customerInfo = getByCustomernumber(customerNumber);
        Integer integral = customerInfo.getIntegral();
        //更改顶价之后用户剩余的积分
        customerInfo.setIntegral(integral - DataConfig.deductIntegral);
        //更新用户信息
        customerMapper.updateById(customerInfo);
        //添加一条订单信息
        SysOrder newOrder = new SysOrder();
        newOrder.setCustomerId(customerInfo.getId());
        newOrder.setCommodity(url);
        newOrder.setStatus(1);
        newOrder.setDeductIntegral(DataConfig.deductIntegral);
        //保存订单信息
        orderService.save(newOrder);
    }
}
