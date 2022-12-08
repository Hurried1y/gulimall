package com.example.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.CategoryBrandRelationEntity;
import com.example.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.product.dao.CategoryDao;
import com.example.gulimall.product.entity.CategoryEntity;
import com.example.gulimall.product.service.CategoryService;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public List<CategoryEntity> listWithTree() {
        //查出所有分类
        List<CategoryEntity> list = list();
        //组装成树形结构
        //1.找到所有的一级分类
        List<CategoryEntity> level1Menus = list.stream().filter(item -> item.getParentCid() == 0
        ).map((menu)->{
            menu.setChildren(getChildren(menu, list));
            return menu;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
        return level1Menus;

    }

    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream().filter(item -> {
            return item.getParentCid().equals(root.getCatId());
        }).map(item -> {
            //找到子菜单
            item.setChildren(getChildren(item, all));
            return item;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
        return children;
    }

    @Override
    public void removeMenusByIds(Long[] catIds) {
        //TODO 检查当前删除的菜单是否在别处被引用


        removeByIds(Arrays.asList(catIds));
    }

    @Override
    public List<Long> getCatelogPath(Long catelogId) {
        List<Long> catelogPath = new ArrayList<>();
        CategoryEntity category = getById(catelogId);
        catelogPath.add(catelogId);
        while(category.getParentCid() != 0){
            category = getById(category.getParentCid());
            catelogPath.add(category.getCatId());
        }
        Collections.reverse(catelogPath);
        return catelogPath;
    }

    @Override
    public R updateCategory(CategoryEntity categoryEntity) {
        updateById(categoryEntity);
        if(StringUtils.hasLength(categoryEntity.getName())){
            //级联更新关系表
            LambdaUpdateWrapper<CategoryBrandRelationEntity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(CategoryBrandRelationEntity::getBrandName, categoryEntity.getName());
            categoryBrandRelationService.update(updateWrapper);
        }
        return R.ok();
    }

}