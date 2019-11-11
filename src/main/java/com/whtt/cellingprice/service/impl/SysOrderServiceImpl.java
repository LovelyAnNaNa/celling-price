package com.whtt.cellingprice.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.whtt.cellingprice.common.Constant;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.whtt.cellingprice.entity.pojo.SysOrder;
import com.whtt.cellingprice.mapper.SysOrderMapper;
import com.whtt.cellingprice.service.SysCustomerService;
import com.whtt.cellingprice.service.SysOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
    public int getSumDeductintegral(Integer status, String date) {
        return orderMapper.getSumDeductintegral(status,date);
    }

    @Override
    public int getOrderCount(Integer status, String date) {
        QueryWrapper<SysOrder> orderQueryWrapper = new QueryWrapper<>();

        if (status != null) {
            orderQueryWrapper.eq("status",status);
        }
        if(StringUtils.isNotBlank(date)){
            orderQueryWrapper.like("create_time",date);
        }
        return orderMapper.selectCount(orderQueryWrapper);
    }

    @Override
    public List<SysOrder> getCustomerOrder(String customerNumber, String date) {
        QueryWrapper<SysOrder> orderQueryWrapper = new QueryWrapper<>();
        //判断是否根据用户账号查询
        if(StringUtils.isNotBlank(customerNumber)){
            SysCustomer customerInfo = customerService.getByCustomernumber(customerNumber);
            orderQueryWrapper.eq("customer_id",customerInfo.getId());
        }
        //判断是否根据日期查询
        if(StringUtils.isNotBlank(date)){
            orderQueryWrapper.like("create_time",date);
        }
        return orderMapper.selectList(orderQueryWrapper);
    }

    @Override
    public List<SysOrder> getOrderList(Integer page, Integer limit, String customerName, String rangeIntegral,Integer status,
                                       String startTime,String endTime) {
        QueryWrapper<SysOrder> orderQueryWrapper = new QueryWrapper<>();
        //判断是否有用户名作为条件
        if(StringUtils.isNotBlank(customerName)){
            //用户列表
            List<SysCustomer> customerList = customerService.getByFuzzyCustomername(customerName);
            if(customerList != null && customerList.size() > 0){
                //根据用户id查询
                ArrayList<Integer> idList = new ArrayList<>();
                customerList.forEach(customer -> idList.add(customer.getId()));
                orderQueryWrapper.in("customer_id",idList);
            }else{
                //如果根据用户名模糊查询不到信息,直接返回空集合
                return new ArrayList(0);
            }
        }

        //根据积分的价格区间查询
        if(StringUtils.isNotBlank(rangeIntegral)){
            String[] integral = rangeIntegral.split("-");
            if(integral != null && integral.length == 2){
                orderQueryWrapper.between("deduct_integral",integral[0],integral[1]);
            }
        }

        //根据订单创建时间查询
        if(StringUtils.isNotBlank(startTime)){
            //如果没有终止时间,默认当前时间为终止事件
            if(StringUtils.isBlank(endTime)) {
                endTime = DateUtil.format(new Date(), Constant.FORMAT_DATE_TIME);
            }
            orderQueryWrapper.between("create_time",startTime,endTime);
        }

        if(status != null){
            orderQueryWrapper.eq("status",status);
        }

        orderQueryWrapper.orderByDesc("id");
        PageHelper.startPage(page,limit);
        List<SysOrder> orderList = orderMapper.selectList(orderQueryWrapper);
        getCascadeInfo(orderList);
        return orderList;
    }

    @Override
    public List<SysOrder> getCascadeInfo(List<SysOrder> orderList) {
        orderList.forEach(order -> order.setCustomer(customerService.getById(order.getCustomerId())));
        return orderList;
    }
}
