package com.example.gulimall.coupon.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.gulimall.common.to.SkuFullReductionTo;
import com.example.gulimall.common.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.coupon.entity.SkuFullReductionEntity;
import com.example.gulimall.coupon.service.SkuFullReductionService;




/**
 * 商品满减信息
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-29 21:40:14
 */
@RestController
@RequestMapping("/coupon/skufullreduction")
public class SkuFullReductionController {
    @Autowired
    private SkuFullReductionService skuFullReductionService;

    @PostMapping("/save")
    public R save(@RequestBody List<SkuFullReductionTo> fullReductionTos){
        List<SkuFullReductionEntity> fullReductionEntities = new ArrayList<>();
        BeanUtils.copyProperties(fullReductionTos, fullReductionEntities);
        skuFullReductionService.saveBatch(fullReductionEntities);
        return R.ok();
    }
}
