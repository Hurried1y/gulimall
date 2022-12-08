package com.example.gulimall.coupon.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.gulimall.common.to.SkuLadderTo;
import com.example.gulimall.common.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gulimall.coupon.entity.SkuLadderEntity;
import com.example.gulimall.coupon.service.SkuLadderService;




/**
 * 商品阶梯价格
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-29 21:40:14
 */
@RestController
@RequestMapping("/coupon/skuladder")
public class SkuLadderController {
    @Autowired
    private SkuLadderService skuLadderService;

    @RequestMapping("/save")
    public R saveSkuLadders(@RequestBody List<SkuLadderTo> skuLadderTos){
        List<SkuLadderEntity> ladders = new ArrayList<>();
        BeanUtils.copyProperties(skuLadderTos, ladders);
        skuLadderService.saveBatch(ladders);
        return R.ok();
    }
}
