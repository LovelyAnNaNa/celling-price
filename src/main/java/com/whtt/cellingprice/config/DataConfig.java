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
    public static Integer cellingIntegral;
    //用户违约所需扣除的积分
    public static Integer violateIntegral;

    /**
     * 来信账号
     */
    public static String laixinUsername;

    /**
     * 来信密码
     */
    public static String laixinPassword;

    /**
     * 来信微拍堂项目id
     */
    public static String laixinId;


    public static Integer getDeductIntegral(int status){
        if(status == 1){
            return cellingIntegral;
        }
        if(status == 2){
            return violateIntegral;
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SysConfig deductConfig = configService.getOne(new QueryWrapper<SysConfig>().eq("config_key", "celling_integral"));
        cellingIntegral = Integer.valueOf(deductConfig.getConfigValue());

        SysConfig violateConfig = configService.getOne(new QueryWrapper<SysConfig>().eq("config_key", "violate_integral"));
        violateIntegral = Integer.valueOf(violateConfig.getConfigValue());

        SysConfig usernameConfig = configService.getOne(new QueryWrapper<SysConfig>().eq("config_key", "laixin_username"));
        laixinUsername = usernameConfig.getConfigValue();

        SysConfig passwordConfig = configService.getOne(new QueryWrapper<SysConfig>().eq("config_key", "laixin_password"));
        laixinPassword = passwordConfig.getConfigValue();

        SysConfig laixinIdConfig = configService.getOne(new QueryWrapper<SysConfig>().eq("config_key", "laixin_id"));
        laixinId = laixinIdConfig.getConfigValue();
    }
}
