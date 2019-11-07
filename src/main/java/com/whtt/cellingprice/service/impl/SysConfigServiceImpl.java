package com.whtt.cellingprice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whtt.cellingprice.config.DataConfig;
import com.whtt.cellingprice.entity.pojo.SysConfig;
import com.whtt.cellingprice.mapper.SysConfigMapper;
import com.whtt.cellingprice.service.SysConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public int updateConfig(List<SysConfig> configList) {
        configList.forEach(config -> {
            configMapper.updateById(config);
        });
        return 0;
    }
}
