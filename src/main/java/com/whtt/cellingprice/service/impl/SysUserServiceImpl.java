package com.whtt.cellingprice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whtt.cellingprice.entity.pojo.SysUser;
import com.whtt.cellingprice.mapper.SysUserMapper;
import com.whtt.cellingprice.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    private  SysUserMapper sysUserMapper;

    @Override
    public int selectLogin(String username, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper= Wrappers.<SysUser>lambdaQuery();
          queryWrapper.eq(SysUser::getUsername,username).lt(SysUser::getPassword,password);
          return sysUserMapper.selectCount(queryWrapper);
    }

    @Override
    public int changePassword(String username, String password) {
        SysUser sysUser =new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        LambdaQueryWrapper<SysUser> queryWrapper= Wrappers.<SysUser>lambdaQuery();
        queryWrapper.eq(SysUser::getUsername,username);
        return sysUserMapper.update(sysUser,queryWrapper);
    }

}
