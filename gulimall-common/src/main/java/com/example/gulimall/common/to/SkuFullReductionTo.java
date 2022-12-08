package com.example.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * User：Hurried1y
 * Date：2022/12/1
 * Description：
 */
@Data
public class SkuFullReductionTo {
    /**
     * spu_id
     */
    private Long skuId;
    /**
     * 满多少
     */
    private BigDecimal fullPrice;
    /**
     * 减多少
     */
    private BigDecimal reducePrice;
    /**
     * 是否参与其他优惠
     */
    private Integer addOther;
}
