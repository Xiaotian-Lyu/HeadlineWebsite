package com.atguigu.pojo.vo;

import lombok.Data;

/**
 * 来接收前端portal Controller---findNewsPage的数据
 */
@Data
//可以自动生成 Java 类中的一些常见方法，如 toString、hashCode、equals、Getter 和 Setter 等
public class PortalVo {
    private String keyWords;
    private Integer type;
    private Integer pageNum = 1;
    private Integer pageSize =10;

}
