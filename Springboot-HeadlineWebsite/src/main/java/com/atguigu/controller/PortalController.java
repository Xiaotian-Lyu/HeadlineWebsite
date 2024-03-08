package com.atguigu.controller;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.service.HeadlineService;
import com.atguigu.service.TypeService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController  //注解的类通常包含多个处理HTTP请求的方法，这些方法的返回值会被直接转换成JSON或其他格式的响应体。
@RequestMapping("portal")
@CrossOrigin  //跨域
public class PortalController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private HeadlineService headlineService;

    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        Result result = typeService.finaAllTypes();
        return result;
    }

    @PostMapping("findNewsPage")
    //通常用于提交表单数据或执行对服务器端数据的修改操作。将方法映射到指定路径的POST请求
    // @GetMapping通常用于获取资源或执行对服务器端数据的只读操作。
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewsPage(portalVo);
        return  result;
    }

    @PostMapping("showHeadlineDetail")
    //通常用于提交表单数据或执行对服务器端数据的修改操作。将方法映射到指定路径的POST请求
    // @GetMapping通常用于获取资源或执行对服务器端数据的只读操作。
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }

}
