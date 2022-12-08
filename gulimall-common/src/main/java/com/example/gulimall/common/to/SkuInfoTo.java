package com.example.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * User：Hurried1y
 * Date：2022/12/6
 * Description：
 */
@Data
public class SkuInfoTo {
    private Long skuId;
    private Long spuId;
    private String skuName;
    private String skuDesc;
    private Long catalogId;
    private Long brandId;
    private String skuDefaultImg;
    private String skuTitle;
    private String skuSubtitle;
    private BigDecimal price;
    private Long saleCount;
}
