package com.example.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.product.entity.CategoryEntity;
import com.example.gulimall.product.service.CategoryService;




/**
 * 商品三级分类
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:20
 */
@RestController
@RequestMapping("/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类及其子分类，以树形结构组装起来
     * @return
     */
    @GetMapping("/list/tree")
    public R list(){
        List<CategoryEntity> categoryEntityList = categoryService.listWithTree();
        return R.ok().put("data", categoryEntityList);
    }

    @GetMapping("/info/{catId}")
    public R getCategoryInfo(@PathVariable Long catId){
        CategoryEntity categoryEntity = categoryService.getById(catId);
        return R.ok().put("data", categoryEntity);
    }

    @PostMapping("/deleteCategory")
    public R delete(@RequestBody Long catId){
        categoryService.removeById(catId);
        return R.ok();
    }

    @PostMapping("/deleteCategorys")
    public R delete(@RequestBody Long[] catIds){
        categoryService.removeMenusByIds(catIds);
        return R.ok();
    }

    @PostMapping("/addCategory")
    public R addCategory(@RequestBody CategoryEntity categoryEntity){
        categoryService.save(categoryEntity);
        return R.ok();
    }

    @PostMapping("/editCategory")
    public R editCategory(@RequestBody CategoryEntity categoryEntity){
        return categoryService.updateCategory(categoryEntity);
    }

    //实现拖拽功能的数据更新
    @PostMapping("/updateDrop")
    public R updateLevel(@RequestBody List<CategoryEntity> categoryEntities){
        categoryService.updateBatchById(categoryEntities);
        return R.ok();
    }
}
