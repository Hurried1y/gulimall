package com.example.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.service.BrandService;
import com.example.gulimall.product.service.CategoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.product.entity.CategoryBrandRelationEntity;
import com.example.gulimall.product.service.CategoryBrandRelationService;




/**
 * 品牌分类关联
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:20
 */
@RequestMapping("/product/categorybrandrelation")
@RestController
public class CategoryBrandRelationController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @GetMapping("/catelog/list")
    public R list(@RequestParam Map<String, Object> params){
        return categoryBrandRelationService.getList(params);
    }

    @PostMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelationEntity){
        return categoryBrandRelationService.saveEntity(categoryBrandRelationEntity);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long id){
        categoryBrandRelationService.removeById(id);
        return R.ok();
    }

    @GetMapping("/brands/list")
    public R getBrandList(@Param("catId") Long catId){
        return brandService.getBrandsByCatId(catId);
    }
}
