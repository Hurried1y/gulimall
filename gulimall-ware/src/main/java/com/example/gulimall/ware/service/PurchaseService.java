package com.example.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.entity.PurchaseEntity;
import com.example.gulimall.ware.vo.PurchaseDoneVo;
import com.example.gulimall.ware.vo.PurchaseMergeVo;

import java.util.Map;

/**
 * 采购信息
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:29:57
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    R getList(Map<String, Object> params);

    R merge(PurchaseMergeVo mergeVo);

    R received(Long[] ids);

    R done(PurchaseDoneVo doneVo);
}

