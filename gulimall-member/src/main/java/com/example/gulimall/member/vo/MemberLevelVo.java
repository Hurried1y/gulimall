package com.example.gulimall.member.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * User：Hurried1y
 * Date：2022/11/27
 * Description：
 */
@Data
public class MemberLevelVo {
    /**
     * 等级名称
     */
    private String name;
    /**
     * 等级需要的成长值
     */
    private Integer growthPoint;
    /**
     * 是否为默认等级[0->不是；1->是]
     */
    private Integer defaultStatus;
    /**
     * 免运费标准
     */
    private BigDecimal freeFreightPoint;
    /**
     * 每次评价获取的成长值
     */
    private Integer commentGrowthPoint;
    /**
     * 是否有免邮特权
     */
    private Integer priviledgeFreeFreight;
    /**
     * 是否有会员价格特权
     */
    private Integer priviledgeMemberPrice;
    /**
     * 是否有生日特权
     */
    private Integer priviledgeBirthday;
    /**
     * 备注
     */
    private String note;
}

