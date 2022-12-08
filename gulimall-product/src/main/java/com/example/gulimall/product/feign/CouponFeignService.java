package com.example.gulimall.product.feign;

import com.example.gulimall.common.to.MemberPriceTo;
import com.example.gulimall.common.to.SkuFullReductionTo;
import com.example.gulimall.common.to.SkuLadderTo;
import com.example.gulimall.common.to.SpuBoundTo;
import com.example.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * User：Hurried1y
 * Date：2022/11/29
 * Description：
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skuladder/save")
    R saveSkuLadders(@RequestBody List<SkuLadderTo> skuLadderTos);

    @PostMapping("/coupon/skufullreduction/save")
    R saveSkuFullReduction(@RequestBody List<SkuFullReductionTo> fullReductionTos);

    @PostMapping("save")
    R saveMemberPrice(@RequestBody List<MemberPriceTo> priceTos);

}
