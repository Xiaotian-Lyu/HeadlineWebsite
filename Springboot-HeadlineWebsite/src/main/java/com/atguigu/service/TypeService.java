package com.atguigu.service;

import com.atguigu.pojo.Type;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author tianlyu
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-03-06 18:49:05
*/
public interface TypeService extends IService<Type> {
    /**
     * 查询所有类别的数据
     * @return
     */
    Result finaAllTypes();
}
