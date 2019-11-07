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
import java.util.List;

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
    public boolean checkIntegral(String customerNumber, Integer status) {
        //获取账号对应的用户信息
        SysCustomer customerInfo = getByCustomernumber(customerNumber);
        //获取所需要扣除的积分
        Integer deductIntegral = DataConfig.getDeductIntegral(status);
        return deductIntegral > customerInfo.getIntegral();
    }

    @Override
    public void addOrder(String commodity, String customerNumber,Integer status) {
        //获取用户信息
        SysCustomer customerInfo = getByCustomernumber(customerNumber);
        Integer integral = customerInfo.getIntegral();
        //本次交易扣除的积分,为顶价成功扣除的积分,2为用户违约扣除的积分
        int deductInegral = DataConfig.getDeductIntegral(status);
        //重新设置用户的积分
        customerInfo.setIntegral(integral - deductInegral);
        //更新用户信息
        customerMapper.updateById(customerInfo);

        //添加一条订单信息
        SysOrder newOrder = new SysOrder();
        //设置新用户id
        newOrder.setCustomerId(customerInfo.getId());
        //设置商品信息
        newOrder.setCommodity(commodity);
        newOrder.setStatus(status);
        //本次扣除的积分
        newOrder.setDeductIntegral(deductInegral);
        //保存订单信息
        orderService.save(newOrder);
    }

    @Override
    public List<SysCustomer> getByFuzzyCustomername(String customerName) {
        QueryWrapper<SysCustomer> customerQueryWrapper = new QueryWrapper<>();
        customerQueryWrapper.like("customer_name",customerName);
        return customerMapper.selectList(customerQueryWrapper);
    }
}
