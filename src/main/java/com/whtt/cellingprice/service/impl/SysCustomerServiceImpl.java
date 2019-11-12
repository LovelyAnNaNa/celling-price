package com.whtt.cellingprice.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.whtt.cellingprice.common.CommonResult;
import com.whtt.cellingprice.common.Constant;
import com.whtt.cellingprice.config.DataConfig;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.whtt.cellingprice.entity.pojo.SysOrder;
import com.whtt.cellingprice.entity.pojo.SysRecharge;
import com.whtt.cellingprice.mapper.SysCustomerMapper;
import com.whtt.cellingprice.service.SysCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.cellingprice.service.SysOrderService;
import com.whtt.cellingprice.service.SysRechargeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
    @Autowired
    private SysRechargeService rechargeService;

    @Override
    public void delCustomerInfo(Integer id) {
        //删除用户信息
        customerMapper.deleteById(id);
        //删除用户的积分充值记录
        UpdateWrapper<SysRecharge> rechargeUpdateWrapper = new UpdateWrapper<>();
        rechargeUpdateWrapper.eq("customer_id",id);
        rechargeService.remove(rechargeUpdateWrapper);
    }

    @Override
    public List<SysCustomer> getCustomerList(Integer page, Integer limit, String customerName, String rangeIntegral, String startTime, String endTime) {
        QueryWrapper<SysCustomer> customerQueryWrapper = new QueryWrapper<>();
        //用户名模糊查询
        if(StringUtils.isNotBlank(customerName)){
            customerQueryWrapper.like("customer_name",customerName);
        }
        //价格区间
        if(StringUtils.isNotBlank(rangeIntegral)){
            String[] range = rangeIntegral.split("-");
            if(range.length == 2){
                customerQueryWrapper.between("integral",range[0],range[1]);
            }
        }
        //根据用户添加时间查询
        if(StringUtils.isNotBlank(startTime)){
            //如果没有终止时间,默认当前时间为终止事件
            if(StringUtils.isBlank(endTime)) {
                endTime = DateUtil.format(new Date(), Constant.FORMAT_DATE_TIME);
            }
            customerQueryWrapper.between("create_time",startTime,endTime);
        }
        PageHelper.startPage(page,limit);
        return customerMapper.selectList(customerQueryWrapper);
    }

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

    /**
     * 新增账号或增加积分
     *
     * @param customerName
     * @param customerNumber
     * @param integral
     * @return
     */
    @Override
    public CommonResult insertCustomerOrAddIntegral(String customerName, String customerNumber, Integer integral) {
        SysCustomer customer = getByCustomernumber(customerNumber);
        boolean flag;
        int type;
        if (null != customer) {
            //存在账号新增积分
            customer.setIntegral(customer.getIntegral() + integral);
            flag = customer.updateById();
            type = 0;
        } else {
            customer = new SysCustomer();
            customer.setCustomerName(customerName);
            customer.setCustomerNumber(customerNumber);
            customer.setIntegral(integral);
            flag = customer.insert();
            type = 1;
        }

        if (flag) {
            if (type == 1) {
                return CommonResult.success(customerName + "（" + customerNumber + "），你好" + "开通账号成功，积分余额为：" + customer.getIntegral());
            } else {
                return CommonResult.success(customerName + "（" + customerNumber + "），你好" + "充值积分，积分余额为：" + customer.getIntegral());
            }
        }

        return CommonResult.failed();
    }
}
