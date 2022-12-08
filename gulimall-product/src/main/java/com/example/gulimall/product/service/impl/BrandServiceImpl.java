package com.example.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.AttrEntity;
import com.example.gulimall.product.entity.CategoryBrandRelationEntity;
import com.example.gulimall.product.entity.CategoryEntity;
import com.example.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.product.dao.BrandDao;
import com.example.gulimall.product.entity.BrandEntity;
import com.example.gulimall.product.service.BrandService;
import org.springframework.util.StringUtils;


@Service
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        String key = params.get("key").toString();
        LambdaQueryWrapper<BrandEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasLength(key)&& !"".equals(key)) {
            queryWrapper.like(BrandEntity::getName, key).or()
                    .eq(BrandEntity::getBrandId, key);
        }
        Page<BrandEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        return new PageUtils(pageInfo);
    }

    @Override
    public R updateBrand(BrandEntity brandEntity) {
        updateById(brandEntity);
        if(StringUtils.hasLength(brandEntity.getName())){
            //级联更新关系表
            LambdaUpdateWrapper<CategoryBrandRelationEntity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(CategoryBrandRelationEntity::getBrandName, brandEntity.getName());
            categoryBrandRelationService.update(updateWrapper);
        }
        return R.ok();
    }

    @Override
    public R getBrandsByCatId(Long catId) {
        LambdaUpdateWrapper<CategoryBrandRelationEntity> relationQuery = new LambdaUpdateWrapper<>();
        relationQuery.eq(CategoryBrandRelationEntity::getCatelogId, catId);
        List<CategoryBrandRelationEntity> relationList = categoryBrandRelationService.list(relationQuery);
        List<Long> brandIds = relationList.stream().map(CategoryBrandRelationEntity::getBrandId).collect(Collectors.toList());
        List<BrandEntity> brandEntities = listByIds(brandIds);
        return R.ok().put("data", brandEntities);
    }
}