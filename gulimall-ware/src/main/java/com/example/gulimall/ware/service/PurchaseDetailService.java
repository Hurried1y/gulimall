package com.example.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:29:57
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    R getList(Map<String, Object> params);

    R changeStatusByPurchaseId(Long id);
}

