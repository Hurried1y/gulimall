package com.example.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.example.gulimall.product.entity.AttrGroupEntity;

import java.util.List;
import java.util.Map;


/**
 * 属性分组
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:19
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils getAttrGroupList(Long catelogId, Map<String, Object> params);

    R getAttrGroupInfo(Long attrGroupId);

    R getAttrGroupWithAttrByCatelogId(Long catelogId);
}

