package com.whtt.cellingprice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.cellingprice.entity.pojo.SysUser;
import com.whtt.cellingprice.mapper.SysUserMapper;
import com.whtt.cellingprice.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 管理员用户表 服务实现类
 * </p>
 *
 * @author shj
 * @since 2019-11-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List selectLogin(String username, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("username", username).eq("password", password);

        return sysUserMapper.selectList(queryWrapper);
    }

    @Override
    public int changePassword(String pass ,String word) {
        SysUser sysUser = new SysUser();
        sysUser.setPassword(word);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("password", pass);
        return sysUserMapper.update(sysUser, queryWrapper);

    }

}
