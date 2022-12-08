package com.example.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.AttrEntity;
import com.example.gulimall.product.entity.ProductAttrValueEntity;
import com.example.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;


/**
 * 商品属性
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:19
 */
public interface AttrService extends IService<AttrEntity> {

    R getAttrList(Map<String, Object> params, Long catelogId, Integer attrType);

    R saveAttr(AttrVo attrVo);

    R getInfo(Long attrId);

    R updateAttr(AttrVo attrVo);

    R getAttrByAttrGroupId(Long attrGroupId);

    R getNoRelationAttrByAttrGruopId(Map<String, Object> params, Long attrGroupId);

    R getListForSpu(Long spuId);

    R updateAttrValue(Long id, List<ProductAttrValueEntity> attrValues);
}

