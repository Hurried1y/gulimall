package com.example.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:29:57
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    R getList(Map<String, Object> params);

    R addStock(Long skuId, Long wareId, Integer skuNum);
}

