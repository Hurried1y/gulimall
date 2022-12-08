package com.example.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.CategoryBrandRelationEntity;

import java.util.Map;


/**
 * 品牌分类关联
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:20
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    R getList(Map<String, Object> params);

    R saveEntity(CategoryBrandRelationEntity categoryBrandRelationEntity);

}

