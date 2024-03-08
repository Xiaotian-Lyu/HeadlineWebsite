package com.atguigu.controller;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController  //注解的类通常包含多个处理HTTP请求的方法，这些方法的返回值会被直接转换成JSON或其他格式的响应体。
@RequestMapping("user")
@CrossOrigin //跨域
public class UserController {

    //这一层只负责接值，具体工作由service 来做
    //UserService 是一个接口
    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("login")
    //通常用于提交表单数据或执行对服务器端数据的修改操作。将方法映射到指定路径的POST请求
    // @GetMapping通常用于获取资源或执行对服务器端数据的只读操作。
    public Result login(@RequestBody User user){
        //使用 @RequestBody 注解的参数通常应该是一个POJO（Plain Old Java Object）类，
        // 以便Spring能够正确地将请求体中的数据转换为Java对象。
        //通常包含在POST、PUT等请求中。它是请求的主要部分，用于传递数据给服务器。

        Result result = userService.login(user);
        return result;
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        //是Spring框架中的注解之一，用于从HTTP请求的头部信息中提取值并注入到控制器方法的参数中。
        //例如认证令牌、内容类型等。
        Result result = userService.getUserInfo(token);
        return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(String username){
        Result result = userService.checkUserName(username);
        return result;
    }

    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return result;
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){

        boolean expiration = jwtHelper.isExpiration(token);

        if(expiration){
            //已经过期
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }

}
