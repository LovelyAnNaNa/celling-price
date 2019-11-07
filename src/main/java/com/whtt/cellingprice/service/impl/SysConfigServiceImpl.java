package com.whtt.cellingprice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whtt.cellingprice.config.DataConfig;
import com.whtt.cellingprice.entity.pojo.SysConfig;
import com.whtt.cellingprice.mapper.SysConfigMapper;
import com.whtt.cellingprice.service.SysConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public Boolean updateDeductIntegral(Integer deductIntegral) {
        //获取配置信息
        SysConfig deductConfig = configMapper.selectOne(new QueryWrapper<SysConfig>().eq("config_key", "deduct_integral"));
        deductConfig.setConfigValue(deductIntegral + "");
        //更改配置信息
        int count = configMapper.updateById(deductConfig);
        if (count < 0) {
            return false;
        }
        //更改配置类中的信息
        DataConfig.cellIntegral = deductIntegral;
        return true;
    }
}
