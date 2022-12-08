package com.example.gulimall.coupon.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.gulimall.common.to.MemberPriceTo;
import com.example.gulimall.common.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.coupon.entity.MemberPriceEntity;
import com.example.gulimall.coupon.service.MemberPriceService;




/**
 * 商品会员价格
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-29 21:40:13
 */
@RestController
@RequestMapping("/coupon/memberprice")
public class MemberPriceController {
    @Autowired
    private MemberPriceService memberPriceService;

    @PostMapping("/save")
    public R save(@RequestBody List<MemberPriceTo> priceTos){
        List<MemberPriceEntity> memberPrices = new ArrayList<>();
        BeanUtils.copyProperties(priceTos, memberPrices);
        memberPriceService.saveBatch(memberPrices);
        return R.ok();
    }
}
