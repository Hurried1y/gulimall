package com.example.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * User：Hurried1y
 * Date：2022/12/1
 * Description：
 */
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
