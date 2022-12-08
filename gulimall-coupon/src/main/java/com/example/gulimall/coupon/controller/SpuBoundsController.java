package com.example.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.gulimall.common.to.SpuBoundTo;
import com.example.gulimall.common.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.coupon.entity.SpuBoundsEntity;
import com.example.gulimall.coupon.service.SpuBoundsService;




/**
 * 商品spu积分设置
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-29 21:40:13
 */
@RestController
@RequestMapping("/coupon/spubounds")
public class SpuBoundsController {
    @Autowired
    private SpuBoundsService spuBoundsService;

    @PostMapping("/save")
    public R saveBounds(@RequestBody SpuBoundTo spuBoundTo){
        SpuBoundsEntity spuBounds = new SpuBoundsEntity();
        BeanUtils.copyProperties(spuBoundTo, spuBounds);
        spuBoundsService.save(spuBounds);
        return R.ok();
    }

}
