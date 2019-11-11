package com.whtt.cellingprice.mapper;

import com.whtt.cellingprice.entity.pojo.SysOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wbh
 * @since 2019-11-07
 */
public interface SysOrderMapper extends BaseMapper<SysOrder> {

    int getSumDeductintegral(@Param("status") Integer status, @Param("date") String date);
}
