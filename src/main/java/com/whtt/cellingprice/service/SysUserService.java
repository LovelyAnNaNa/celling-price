package com.whtt.cellingprice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whtt.cellingprice.entity.pojo.SysUser;

/**
 * <p>
 * 管理员用户表 服务类
 * </p>
 *
 * @author shj
 * @since 2019-11-07
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUsernameAndPassword(String username, String password);

    int changePassword(String word,Integer id);
}
