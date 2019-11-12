package com.whtt.cellingprice.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.whtt.cellingprice.common.Constant;
import com.whtt.cellingprice.entity.pojo.SysCustomer;
import com.whtt.cellingprice.entity.pojo.SysRecharge;
import com.whtt.cellingprice.mapper.SysRechargeMapper;
import com.whtt.cellingprice.service.SysCustomerService;
import com.whtt.cellingprice.service.SysRechargeService;
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
public class SysRechargeServiceImpl extends ServiceImpl<SysRechargeMapper, SysRecharge> implements SysRechargeService {

    @Resource
    private SysRechargeMapper rechargeMapper;
    @Autowired
    private SysCustomerService customerService;

    @Override
    public int getSumIntegral(Integer customerId, String date) {
        Integer sumIntegral = rechargeMapper.getSumIntegral(customerId, date);
        return sumIntegral == null ? 0 : sumIntegral;
    }

    @Override
    public List<SysRecharge> getRechargeList(Integer page, Integer limit, String customerName,String rangeIntegral,
                                   String startTime,String endTime) {
        QueryWrapper<SysRecharge> rechargeQueryWrapper = new QueryWrapper<>();
        //根据充值的用户查询
        if(StringUtils.isNotBlank(customerName)){
            //模糊查询和用户名相像的用户
            List<SysCustomer> customerList = customerService.getByFuzzyCustomername(customerName);
            if(customerList != null && customerList.size() > 0){
                ArrayList<Integer> idList = new ArrayList<Integer>(customerList.size());
                customerList.forEach(customer -> idList.add(customer.getId()));
                rechargeQueryWrapper.in("customer_id",idList);
            }else{
                //如果没有该用户名的用户,所有数据都不查询,返回一个空对象
                return new ArrayList<SysRecharge>(0);
//                rechargeQueryWrapper.eq("customer_id","-1");
            }
        }

        //根据充值的区间查询
        if(StringUtils.isNotBlank(rangeIntegral)){
            String[] integral = rangeIntegral.split("-");
            if(integral != null && integral.length == 2){
                rechargeQueryWrapper.between("integral",integral[0],integral[1]);
            }
        }

        //根据订单创建时间查询
        if(StringUtils.isNotBlank(startTime)){
            //如果没有终止时间,默认当前时间为终止事件
            if(StringUtils.isBlank(endTime)) {
                endTime = DateUtil.format(new Date(), Constant.FORMAT_DATE_TIME);
            }
            rechargeQueryWrapper.between("create_time",startTime,endTime);
        }

        PageHelper.startPage(page,limit);
        //查询充值记录信息
        List<SysRecharge> rechargeList = rechargeMapper.selectList(rechargeQueryWrapper);
        //获取级联对象信息
        getCascadeInfo(rechargeList);
        return rechargeList;
    }

    @Override
    public List<SysRecharge> getCascadeInfo(List<SysRecharge> rechargeList) {
        rechargeList.forEach(e -> e.setCustomer(customerService.getById(e.getCustomerId())));
        return rechargeList;
    }

    @Override
    public boolean addRecharge(SysRecharge newRecharge) {
        //保存更改的信息
        int count = rechargeMapper.insert(newRecharge);
        if(count < 1){
            return false;
        }
        //获取要更改的用户信息
        Integer customerId = newRecharge.getCustomerId();
        SysCustomer customerInfo = customerService.getById(customerId);
        //获取用户的积分余额
        Integer integral = customerInfo.getIntegral();
        //重新设置用户的积分余额并更新
        customerInfo.setIntegral(integral + newRecharge.getIntegral());
        customerService.updateById(customerInfo);
        return true;
    }
}
