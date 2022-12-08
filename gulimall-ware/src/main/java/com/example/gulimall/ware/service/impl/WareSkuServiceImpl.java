package com.example.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.to.SkuInfoTo;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.entity.WareInfoEntity;
import com.example.gulimall.ware.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.ware.dao.WareSkuDao;
import com.example.gulimall.ware.entity.WareSkuEntity;
import com.example.gulimall.ware.service.WareSkuService;
import org.springframework.util.StringUtils;


@Service
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {
    @Autowired
    private ProductFeignService productFeignService;

    @Override
    public R getList(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        Long wareId = 0L;
        Long skuId = 0L;
        if(params.get("wareId") != null && !"".equals(params.get("wareId").toString())){
            wareId = Long.parseLong(params.get("wareId").toString());
        }
        if(params.get("skuId") != null && !"".equals(params.get("skuId").toString())){
            skuId = Long.parseLong(params.get("skuId").toString());
        }
        LambdaQueryWrapper<WareSkuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(wareId!=0L, WareSkuEntity::getWareId, wareId);
        queryWrapper.eq(skuId!=0L, WareSkuEntity::getSkuId, skuId);
        IPage<WareSkuEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        PageUtils pageUtils = new PageUtils(pageInfo);
        return R.ok().put("page", pageUtils);
    }

    @Override
    public R addStock(Long skuId, Long wareId, Integer skuNum) {
        LambdaQueryWrapper<WareSkuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WareSkuEntity::getSkuId, skuId);
        queryWrapper.eq(WareSkuEntity::getWareId, wareId);
        WareSkuEntity wareSku = getOne(queryWrapper);
        if(wareSku == null){
            //新增库存
            wareSku = new WareSkuEntity();
            wareSku.setSkuId(skuId);
            wareSku.setWareId(wareId);
            wareSku.setStock(skuNum);
            wareSku.setStockLocked(0);
            R skuName = productFeignService.getSkuName(skuId);
            wareSku.setSkuName((String) skuName.get("skuName"));
            save(wareSku);
        }
        else {
            //追加库存
            wareSku.setStock(wareSku.getStock()+skuNum);
            updateById(wareSku);
        }
        return R.ok();
    }
}