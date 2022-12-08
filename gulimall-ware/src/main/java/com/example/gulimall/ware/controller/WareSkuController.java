package com.example.gulimall.ware.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.ware.entity.WareSkuEntity;
import com.example.gulimall.ware.service.WareSkuService;




/**
 * 商品库存
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:29:57
 */
@RestController
@RequestMapping("/ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    @GetMapping("/list")
    public R getList(@RequestParam Map<String, Object> params){
        return wareSkuService.getList(params);
    }

    @PostMapping("/save")
    public R save(@RequestBody WareSkuEntity wareSku){
        wareSkuService.save(wareSku);
        return R.ok();
    }

    @GetMapping("/info/{id}")
    public R getInfo(@PathVariable("id") Long id){
        WareSkuEntity wareSku = wareSkuService.getById(id);
        return R.ok().put("wareSku", wareSku);
    }

    @PostMapping("/update")
    public R update(@RequestBody WareSkuEntity wareSku){
        wareSkuService.updateById(wareSku);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        wareSkuService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
