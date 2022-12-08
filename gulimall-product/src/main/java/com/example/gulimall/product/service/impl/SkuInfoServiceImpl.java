package com.example.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.AttrEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.product.dao.SkuInfoDao;
import com.example.gulimall.product.entity.SkuInfoEntity;
import com.example.gulimall.product.service.SkuInfoService;
import org.springframework.util.StringUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {


    @Override
    public R getSkuList(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        Long catelogId = 0L;
        Long brandId = 0L;
        Long min = 0L;
        Long max = 0L;
        if(params.get("catelogId") != null && !"".equals(params.get("catelogId").toString())) {
            catelogId = Long.parseLong((String) params.get("catelogId"));
        }
        if(params.get("brandId") != null && !"".equals(params.get("brandId").toString())) {
            brandId = Long.parseLong(params.get("brandId").toString());
        }
        if(params.get("min") != null) {
            min = Long.parseLong(params.get("min").toString());
        }
        if(params.get("max") != null) {
            max = Long.parseLong(params.get("max").toString());
        }
        String key = (String) params.get("key");

        Page<SkuInfoEntity> pageInfo = new Page<>(page, limit);
        LambdaQueryWrapper<SkuInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasLength(key)&& !"".equals(key)) {
            queryWrapper.like(SkuInfoEntity::getSkuName, key).or()
                    .eq(SkuInfoEntity::getSkuId, key);
        }
        queryWrapper.eq(catelogId!=0L, SkuInfoEntity::getCatalogId, catelogId);
        queryWrapper.eq(brandId!=0L, SkuInfoEntity::getBrandId, brandId);
        if((min!=0L || max!=0L) && min <= max){
            queryWrapper.ge(SkuInfoEntity::getPrice, min);
            queryWrapper.le(SkuInfoEntity::getPrice, max);
        }
        page(pageInfo, queryWrapper);
        return R.ok().put("page", new PageUtils(pageInfo));
    }
}