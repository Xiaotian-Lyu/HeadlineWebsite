package com.atguigu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectCount;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tianlyu
 * @description 针对表【news_user】的数据库操作Service实现
 * @createDate 2024-03-06 18:49:05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    //进行数据库操作 需要userMapper
    @Autowired
    //自动装配Bean： 当Spring容器初始化Bean时，它会尝试寻找匹配的Bean，并将其自动注入到目标Bean中。
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 用实现类来实现User service接口，实现具体业务
     * 1. 账号进行数据库查询 返回用户对象
     * 2. 对比用户密码(md5加密)
     * 3. 成功,根据userId生成token -> map key=token value=token值 - result封装
     * 4. 失败,判断账号还是密码错误,封装对应的枚举错误即可
     *
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {

        //是 MyBatis-Plus 框架中的一个查询条件构造器。它是基于 Lambda 表达式的一种方式，用于构建数据库查询条件
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        /**
         * User::getUsername：这是对 User 类的 getUsername 方法的方法引用。
         * 它是一个简写的表示法，用于创建一个表示 User 类中 username 属性的 getter 方法的 Lambda 表达式。
         * user.getUsername()：这是你希望进行比较的值。它从 user 对象中获取 username 属性的值。
         */
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);

        if(loginUser == null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //对比密码 密码先加密
        if(!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())){
            //登陆成功

            //根据用户id 生产token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));

            // 将token封装到result 并返回
            Map data =new HashMap<>();
            data.put("token",token);

            return Result.ok(data);
        }

        //密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }


    /**
     * 1.获取token,判断是否在有效期
     * 2.根据token解析userid
     * 3.根据用户id 查询数据
     * 4.去掉密码 封装result结果 并返回
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        //判断是否过期
        boolean expiration = jwtHelper.isExpiration(token);

        if(expiration){
            //失效
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        int userId = jwtHelper.getUserId(token).intValue();

        User user = userMapper.selectById(userId);
        user.setUserPwd("");

        Map data = new HashMap();
        data.put("loginUser",user);

        return Result.ok(data);
    }

    /**
     * 检查账号是否可用
     * 根据账号count 进行查询
     * count == 0 可以用
     * count > 0 不可以
     * @param username 账号
     * @return
     */
    @Override
    public Result checkUserName(String username) {

        LambdaQueryWrapper<User> queryWrapper =
                new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        Long count = userMapper.selectCount(queryWrapper);

        if (count == 0) {
            return Result.ok(null);
        }

        return Result.build(null,ResultCodeEnum.USERNAME_USED);
    }

    /**
     * 1 检查账号是否注册了
     * 2。 密码加密处理
     * 3。账号数据保存
     * 4。返回结束
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {

        LambdaQueryWrapper<User> queryWrapper =
                new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);

        if (count > 0) {
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        //密码加密
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));

        userMapper.insert(user);

        return Result.ok(null);
    }
}




