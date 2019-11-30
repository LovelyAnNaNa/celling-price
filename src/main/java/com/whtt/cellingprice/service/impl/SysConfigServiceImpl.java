package com.whtt.cellingprice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.cellingprice.config.DataConfig;
import com.whtt.cellingprice.entity.pojo.SysConfig;
import com.whtt.cellingprice.mapper.SysConfigMapper;
import com.whtt.cellingprice.service.SysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统配置表 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-11-06
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Resource
    private SysConfigMapper configMapper;

    @Override
    public void updateConfig(List<SysConfig> configList) {
        configList.forEach(config -> {
            configMapper.updateById(config);

            if("celling_integral".equals(config.getConfigKey())){
                DataConfig.cellingIntegral = Integer.valueOf(config.getConfigValue());
            } else if ("violate_integral".equals(config.getConfigKey())){
                DataConfig.violateIntegral = Integer.valueOf(config.getConfigValue());
            } else if ("laixin_username".equals(config.getConfigKey())){
                DataConfig.laixinUsername = config.getConfigValue();
            } else if ("laixin_password".equals(config.getConfigKey())){
                DataConfig.laixinPassword = config.getConfigValue();
            } else if ("laixin_id".equals(config.getConfigKey())){
                DataConfig.laixinId = config.getConfigValue();
            } else if ("integral_multiple".equals(config.getConfigKey())){
                DataConfig.integralMultiple = Integer.valueOf(config.getConfigValue());
            }
        });
    }
}
