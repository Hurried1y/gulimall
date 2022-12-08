package com.example.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * User：Hurried1y
 * Date：2022/12/1
 * Description：
 */
@Data
public class MemberPriceTo {
    private Long skuId;
    /**
     * 会员等级id
     */
    private Long memberLevelId;
    /**
     * 会员等级名
     */
    private String memberLevelName;
    /**
     * 会员对应价格
     */
    private BigDecimal memberPrice;
    /**
     * 可否叠加其他优惠[0-不可叠加优惠，1-可叠加]
     */
    private Integer addOther;
}
