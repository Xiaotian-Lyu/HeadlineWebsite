package com.atguigu.interceptors;

import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截器 检查请求头是否有效
 */
@Component //加入ioc 容器
public class LoginProtectedInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * preHandle 是 Spring 框架中拦截器（Interceptor）接口 HandlerInterceptor 的一个方法。
     * 在 Spring MVC 中，拦截器是一种用于在请求处理过程中进行预处理和后处理的机制。
     * HandlerInterceptor 接口定义了三个方法：preHandle、postHandle 和 afterCompletion
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //从请求头中获取token
        String token = request.getHeader("token");

        //检查是否有效
        boolean expiration = jwtHelper.isExpiration(token);

        //有效放行
        if(!expiration){
            return true;
        }

        //无效的返回504
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);

        //是 Jackson 库中的一个核心类，用于实现 Java 对象
        // （POJO，Plain Old Java Object）和 JSON 之间的相互转换。
        ObjectMapper objectMapper = new ObjectMapper();
        //用于将 Java 对象序列化成 JSON 格式的字符串。
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);

        return false;
    }
}
