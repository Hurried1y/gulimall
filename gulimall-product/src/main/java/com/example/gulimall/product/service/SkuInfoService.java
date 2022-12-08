package com.example.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.SkuInfoEntity;

import java.util.Map;


/**
 * sku信息
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:18
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    R getSkuList(Map<String, Object> params);
}

