package com.atguigu.service;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author tianlyu
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-03-06 18:49:05
*/
public interface HeadlineService extends IService<Headline> {
    /**
     * 首页数据查询
     * @param portalVo
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    /**
     * 根据ID查询我们的详情
     * @param hid
     * @return
     */
    Result showHeadlineDetail(Integer hid);

    /**
     * 发布请求
     * @return
     */
    Result publish(Headline headline,String token);

    /**
     * 修改头条
     * @param headline
     * @return
     */
    Result updateData(Headline headline);
}
