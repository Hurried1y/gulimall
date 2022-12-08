package com.example.gulimall.product.vo;

import lombok.Data;

/**
 * User：Hurried1y
 * Date：2022/12/5
 * Description：
 */
@Data
public class AttrForSpu {
    private Long id;
    private Long spuId;
    private Long attrId;
    private String attrName;
    private String attrValue;
    private Integer attrSort;
    private Integer quickShow;
}
