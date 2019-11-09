package com.whtt.cellingprice.service;

import com.whtt.cellingprice.entity.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 管理员用户表 服务类
 * </p>
 *
 * @author shj
 * @since 2019-11-07
 */
public interface SysUserService extends IService<SysUser> {

    int selectLogin(String username, String password);

    int changePassword(String username,String password);
}
