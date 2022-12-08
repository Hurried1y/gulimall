package com.example.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.service.BrandService;
import com.example.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.product.dao.CategoryBrandRelationDao;
import com.example.gulimall.product.entity.CategoryBrandRelationEntity;
import com.example.gulimall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public R getList(Map<String, Object> params) {
        Long brandId = Long.parseLong((String) params.get("brandId"));
        LambdaQueryWrapper<CategoryBrandRelationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryBrandRelationEntity::getBrandId, brandId);
        List<CategoryBrandRelationEntity> list = list(queryWrapper);
        return R.ok().put("data", list);
    }

    @Override
    public R saveEntity(CategoryBrandRelationEntity categoryBrandRelationEntity) {
        Long brandId = categoryBrandRelationEntity.getBrandId();
        Long catelogId = categoryBrandRelationEntity.getCatelogId();
        String brandName = brandService.getById(brandId).getName();
        String categoryName = categoryService.getById(catelogId).getName();
        categoryBrandRelationEntity.setBrandName(brandName);
        categoryBrandRelationEntity.setCatelogName(categoryName);
        save(categoryBrandRelationEntity);
        return R.ok();
    }
}