package com.example.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.vo.SpuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.product.entity.SpuInfoEntity;
import com.example.gulimall.product.service.SpuInfoService;




/**
 * spu信息
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:19
 */
@RestController
@RequestMapping("/product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    @PostMapping("/save")
    public R save(@RequestBody SpuInfoVo spuInfoVo){
        return spuInfoService.saveSpu(spuInfoVo);
    }

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        return spuInfoService.getSpuList(params);
    }

    @PostMapping("/{spuId}/up")
    public R up(@PathVariable("spuId") Long spuId){
        return spuInfoService.up(spuId);
    }
}

