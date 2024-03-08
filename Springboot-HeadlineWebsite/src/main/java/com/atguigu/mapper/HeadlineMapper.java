package com.atguigu.mapper;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author tianlyu
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-03-06 18:49:05
* @Entity com.atguigu.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    IPage<Map> selectMyPage(IPage iPage, @Param("portalVo") PortalVo portalVo);
    //是 MyBatis-Plus（一个基于 MyBatis 的增强工具）中的一个接口，用于支持分页查询操作。
    Map queryDetailMap(Integer hid);

}




