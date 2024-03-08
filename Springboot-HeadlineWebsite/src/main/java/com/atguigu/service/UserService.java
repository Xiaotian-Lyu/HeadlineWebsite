package com.atguigu.service;

import com.atguigu.pojo.User;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author tianlyu
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-03-06 18:49:05
*/
public interface UserService extends IService<User> {
    /**
     * 登陆业务
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 根据token 获取用户数据
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 检查username账号是否可用
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 注册用户
     * @param user
     * @return
     */
    Result regist(User user);
}
