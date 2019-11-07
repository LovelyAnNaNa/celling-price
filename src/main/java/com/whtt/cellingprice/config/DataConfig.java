package com.whtt.cellingprice.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whtt.cellingprice.entity.pojo.SysConfig;
import com.whtt.cellingprice.service.SysConfigService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: wbh
 * @Date: 2019/11/6 16:01
 * @Description:
 */
@Component
public class DataConfig implements InitializingBean {

    @Autowired
    private SysConfigService configService;

    //每次顶价成功时扣除的积分
    public static Integer cellIntegral;
    //用户违约所需扣除的积分
    public static Integer violateIntegral;

    public static Integer getDeductIntegral(int status){
        if(status == 1){
            return cellIntegral;
        }
        if(status == 2){
            return violateIntegral;
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SysConfig deductConfig = configService.getOne(new QueryWrapper<SysConfig>().eq("config_key", "deduct_integral"));
        cellIntegral = Integer.valueOf(deductConfig.getConfigValue());

        SysConfig violateConfig = configService.getOne(new QueryWrapper<SysConfig>().eq("config_key", "violate_integral"));
        violateIntegral = Integer.valueOf(violateConfig.getConfigValue());
    }
}
