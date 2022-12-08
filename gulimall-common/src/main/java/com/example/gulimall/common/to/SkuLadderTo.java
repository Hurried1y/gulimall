package com.example.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * User：Hurried1y
 * Date：2022/12/1
 * Description：
 */
@Data
public class SkuLadderTo {
    private Long skuId;
    /**
     * 满几件
     */
    private Integer fullCount;
    /**
     * 打几折
     */
    private BigDecimal discount;
    /**
     * 折后价
     */
    private BigDecimal price;
    /**
     * 是否叠加其他优惠[0-不可叠加，1-可叠加]
     */
    private Integer addOther;
}
