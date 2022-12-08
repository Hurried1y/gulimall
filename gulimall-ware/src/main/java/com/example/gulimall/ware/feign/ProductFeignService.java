package com.example.gulimall.ware.feign;

import com.example.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User：Hurried1y
 * Date：2022/12/5
 * Description：
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {
    @GetMapping("/product/skuinfo/getSkuPrice")
    R getSkuPrice(@RequestParam Long skuId);

    @GetMapping("/product/skuinfo/getSkuName")
    R getSkuName(@RequestParam Long skuId);
}
