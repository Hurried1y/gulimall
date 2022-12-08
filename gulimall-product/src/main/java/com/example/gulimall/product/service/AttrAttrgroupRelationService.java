package com.example.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.example.gulimall.product.vo.AttrGroupRelationVo;

import java.util.List;


/**
 * 属性&属性分组关联
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:19
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    R addRelations(List<AttrGroupRelationVo> relationVos);

    R deleteRelations(List<AttrGroupRelationVo> relationVos);
}

