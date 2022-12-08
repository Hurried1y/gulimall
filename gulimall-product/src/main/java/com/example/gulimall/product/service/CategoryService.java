package com.example.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.CategoryEntity;

import java.util.List;


/**
 * 商品三级分类
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:20
 */
public interface CategoryService extends IService<CategoryEntity> {

    List<CategoryEntity> listWithTree();

    void removeMenusByIds(Long[] catIds);

    List<Long> getCatelogPath(Long catelogId);

    R updateCategory(CategoryEntity categoryEntity);
}

