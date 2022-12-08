package com.example.gulimall.member.feign;

import com.example.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * User：H11
 * Date：2022/11/14
 * Description：
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @GetMapping("/coupon/test")
    R test();
}
