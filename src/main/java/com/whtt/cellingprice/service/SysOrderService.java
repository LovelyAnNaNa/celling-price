package com.whtt.cellingprice.service;

import com.whtt.cellingprice.entity.pojo.SysOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wbh
 * @since 2019-11-07
 */
public interface SysOrderService extends IService<SysOrder> {

    List<SysOrder> getOrderList(Integer page,Integer limit,String customerName,String rangeIntegral,Integer status,
                                String startTime,String endTime);

    List<SysOrder> getCascadeInfo(List<SysOrder> orderList);
}
