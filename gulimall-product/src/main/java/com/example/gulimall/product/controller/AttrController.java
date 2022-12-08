package com.example.gulimall.product.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.ProductAttrValueEntity;
import com.example.gulimall.product.vo.AttrVo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.product.entity.AttrEntity;
import com.example.gulimall.product.service.AttrService;




/**
 * 商品属性
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:19
 */
@RequestMapping("/product/attr")
@RestController
public class AttrController {
    @Autowired
    private AttrService attrService;

    @GetMapping("/base/list/{catelogId}")
    public R getBaseList(@RequestParam Map<String, Object> params,
                         @PathVariable("catelogId") Long catelogId){
        return attrService.getAttrList(params, catelogId, 1);
    }

    @GetMapping("/base/listforspu/{spuId}")
    public R getListForSpu(@PathVariable("spuId") Long spuId){
        return attrService.getListForSpu(spuId);
    }

    @GetMapping("/info/{attrId}")
    public R getInfo(@PathVariable("attrId") Long attrId){
        return attrService.getInfo(attrId);
    }

    @PostMapping("/save")
    public R save(@RequestBody AttrVo attrVo){
        return attrService.saveAttr(attrVo);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long ids){
        attrService.removeByIds(Collections.singleton(ids));
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody AttrVo attrVo){
        return attrService.updateAttr(attrVo);
    }

    @GetMapping("/sale/list/{catelogId}")
    public R getSaleList(@RequestParam Map<String, Object> params,
                         @PathVariable("catelogId") Long catelogId){
        return attrService.getAttrList(params, catelogId, 0);
    }

    /**
     * 根据spuId修改规格信息
     * @return
     */
    @PostMapping("/update/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody List<ProductAttrValueEntity> attrValues){
        return attrService.updateAttrValue(id, attrValues);
    }
}