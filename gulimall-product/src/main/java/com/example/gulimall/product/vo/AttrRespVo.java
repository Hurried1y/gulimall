package com.example.gulimall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * User：Hurried1y
 * Date：2022/11/25
 * Description：
 */
@Data
public class AttrRespVo extends AttrVo{
    //所属分类
    private String catelogName;
    //所属组名
    private String groupName;
    //完整菜单
    private List<Long> catelogPath;
}
