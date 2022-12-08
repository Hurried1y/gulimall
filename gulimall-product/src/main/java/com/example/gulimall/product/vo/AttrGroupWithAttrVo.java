package com.example.gulimall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * User：Hurried1y
 * Date：2022/12/4
 * Description：
 */
@Data
public class AttrGroupWithAttrVo {
    private Long attrGroupId;
    private String attrGroupName;
    private Integer sort;
    private String descript;
    private String icon;
    private Long catelogId;
    List<Attr> attrs;
}
