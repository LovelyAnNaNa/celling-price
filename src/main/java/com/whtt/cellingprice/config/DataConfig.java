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
    public static Integer deductIntegral;

    @Override
    public void afterPropertiesSet() throws Exception {
        SysConfig deductConfig = configService.getOne(new QueryWrapper<SysConfig>().eq("config_key", "deduct_integral"));
        deductIntegral = Integer.valueOf(deductConfig.getConfigValue());

    }
}
