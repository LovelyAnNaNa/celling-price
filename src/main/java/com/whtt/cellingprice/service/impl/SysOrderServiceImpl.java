package com.whtt.cellingprice.service.impl;

import com.whtt.cellingprice.entity.pojo.SysOrder;
import com.whtt.cellingprice.mapper.SysOrderMapper;
import com.whtt.cellingprice.service.SysCustomerService;
import com.whtt.cellingprice.service.SysOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wbh
 * @since 2019-11-07
 */
@Service
public class SysOrderServiceImpl extends ServiceImpl<SysOrderMapper, SysOrder> implements SysOrderService {

    @Resource
    private SysOrderMapper orderMapper;
    @Autowired
    private SysCustomerService customerService;

    @Override
    public List<SysOrder> getOrderList(Integer page, Integer limit, String customerName, String rangeIntegral) {
        return null;
    }

    @Override
    public List<SysOrder> getCascadeInfo(List<SysOrder> orderList) {
        orderList.forEach(order -> order.setCustomer(customerService.getById(order.getCustomerId())));
        return orderList;
    }
}
