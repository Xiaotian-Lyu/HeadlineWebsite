package com.atguigu.service.impl;

import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Headline;
import com.atguigu.service.HeadlineService;
import com.atguigu.mapper.HeadlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author tianlyu
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-03-06 18:49:05
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 首页数据查询
     * 1 进行分页数据查询
     * 2 分页数据 拼接到result即可
     *
     * 注意：
     * 1 查询需要自定义mapper的方法 携带分页
     * 2 返回结果List<Map>
     *
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {

        IPage<Map> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        //在 MyBatis-Plus 中，IPage 接口定义了分页查询的一些属性和方法，
        // 包括当前页码、每页显示的记录数、总记录数等。
        // 通过使用 IPage，可以更方便地进行分页查询，而不需要手动计算分页的起始位置和偏移量。
        headlineMapper.selectMyPage(page,portalVo);

        List<Map> records = page.getRecords();
        Map data = new HashMap();
        data.put("pageData",records);
        data.put("pageNum",page.getCurrent());
        data.put("pageSize",page.getSize());
        data.put("totalPage",page.getPages());
        data.put("totalSize",page.getTotal());

        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo",data);

        return Result.ok(pageInfo);
    }

    /**
     * 根据ID查询详情
     * 1.修改阅读量[version 乐观锁，当前数据对应的版本号]
     * 2.查询对应的数据【多表 头条 用户表 方法需要自定义-- 返回map】
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map data = headlineMapper.queryDetailMap(hid);
        Map headlineMap = new HashMap();
        headlineMap.put("headline",data);

        //修改阅读量+1
        Headline headline = new Headline();
        headline.setHid((Integer) data.get("hid"));
        headline.setVersion((Integer) data.get("Version"));
        //阅读量+1
        headline.setPageViews((Integer) data.get("pageViews")+1);
        headlineMapper.updateById(headline);

        return Result.ok(headlineMap);
    }

    /**
     * 发布请求
     * 1 补全方法
     * @return
     */
    @Override
    public Result publish(Headline headline,String token) {

        //运用token 查询用户id
        int userid = jwtHelper.getUserId(token).intValue();

        //数据装配
        headline.setPublisher(userid);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        //插入数据
        headlineMapper.insert(headline);

        return Result.ok(null);
    }

    /**
     * 修改头条
     * 1- 查询数据的最新version
     * 2- 修改数据的修改时间为当前节点
     * @param headline
     * @return
     */
    @Override
    public Result updateData(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version);//乐观锁
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }
}




