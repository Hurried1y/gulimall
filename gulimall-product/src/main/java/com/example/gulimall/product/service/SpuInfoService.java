package com.example.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.SpuInfoEntity;
import com.example.gulimall.product.vo.SpuInfoVo;

import java.util.Map;


/**
 * spu信息
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:19
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    R saveSpu(SpuInfoVo spuInfoVo);

    R getSpuList(Map<String, Object> params);

    R up(Long spuId);
}

