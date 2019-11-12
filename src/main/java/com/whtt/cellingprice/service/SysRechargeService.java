package com.whtt.cellingprice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whtt.cellingprice.entity.pojo.SysRecharge;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wbh
 * @since 2019-11-07
 */
public interface SysRechargeService extends IService<SysRecharge> {

    int getSumIntegral(Integer customerId,String date);

    List<SysRecharge> getRechargeList(Integer page,Integer limit,String customerName,String rangeIntegral,
                                      String startTime,String endTime);

    //获取级联对象信息
    List<SysRecharge> getCascadeInfo(List<SysRecharge> rechargeList);

    boolean addRecharge(SysRecharge newRecharge);
}
