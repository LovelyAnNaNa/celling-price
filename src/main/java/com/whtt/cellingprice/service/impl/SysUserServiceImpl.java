package com.whtt.cellingprice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.cellingprice.entity.pojo.SysUser;
import com.whtt.cellingprice.mapper.SysUserMapper;
import com.whtt.cellingprice.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public SysUser getByUsernameAndPassword(String username, String password) {
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        userQueryWrapper.eq("password",password);
        return sysUserMapper.selectOne(userQueryWrapper);
    }

    @Override
    public int changePassword(String word,Integer id) {
        SysUser sysUser=new SysUser();
        sysUser.setPassword(word);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("id", id);
        return sysUserMapper.update(sysUser, queryWrapper);

    }

}
