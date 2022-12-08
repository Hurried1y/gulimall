package com.example.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.gulimall.common.to.SkuInfoTo;
import com.example.gulimall.common.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.product.entity.SkuInfoEntity;
import com.example.gulimall.product.service.SkuInfoService;




/**
 * sku信息
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:18
 */
@RestController
@RequestMapping("/product/skuinfo")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        return skuInfoService.getSkuList(params);
    }

    @GetMapping("/getSkuPrice")
    public R getSkuPrice(@RequestParam Long skuId){
        SkuInfoEntity skuInfoEntity = skuInfoService.getById(skuId);
        return R.ok().put("price", skuInfoEntity.getPrice());
    }

    @GetMapping("/getSkuName")
    public R getSkuName(@RequestParam Long skuId){
        SkuInfoEntity skuInfoEntity = skuInfoService.getById(skuId);
        return R.ok().put("skuName", skuInfoEntity.getSkuName());
    }
}
