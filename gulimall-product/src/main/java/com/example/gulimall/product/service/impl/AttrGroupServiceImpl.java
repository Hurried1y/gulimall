package com.example.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.Query;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.example.gulimall.product.entity.AttrEntity;
import com.example.gulimall.product.entity.CategoryEntity;
import com.example.gulimall.product.service.AttrAttrgroupRelationService;
import com.example.gulimall.product.service.AttrService;
import com.example.gulimall.product.service.CategoryService;
import com.example.gulimall.product.vo.Attr;
import com.example.gulimall.product.vo.AttrGroupWithAttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.product.dao.AttrGroupDao;
import com.example.gulimall.product.entity.AttrGroupEntity;
import com.example.gulimall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrAttrgroupRelationService relationService;
    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils getAttrGroupList(Long catelogId, Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrGroupEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasLength(key)&& !"".equals(key)) {
            queryWrapper.like(AttrGroupEntity::getAttrGroupName, key).or()
                    .eq(AttrGroupEntity::getAttrGroupId, key);
        }
        queryWrapper.eq(catelogId!=0, AttrGroupEntity::getCatelogId, catelogId);
        Page<AttrGroupEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        return new PageUtils(pageInfo);
    }

    @Override
    public R getAttrGroupInfo(Long attrGroupId) {
        AttrGroupEntity groupEntity = getById(attrGroupId);
        Long catelogId = groupEntity.getCatelogId();
        List<Long> catelogPath = categoryService.getCatelogPath(catelogId);
        groupEntity.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", groupEntity);
    }

    @Override
    public R getAttrGroupWithAttrByCatelogId(Long catelogId) {
        LambdaQueryWrapper<AttrGroupEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttrGroupEntity::getCatelogId, catelogId);
        List<AttrGroupEntity> list = list(queryWrapper);
        List<AttrGroupWithAttrVo> vos = new ArrayList<>();
        for (AttrGroupEntity group : list) {
            AttrGroupWithAttrVo vo = new AttrGroupWithAttrVo();
            vo.setAttrGroupId(group.getAttrGroupId());
            vo.setAttrGroupName(group.getAttrGroupName());
            vo.setCatelogId(group.getCatelogId());
            vo.setDescript(group.getDescript());
            vo.setIcon(group.getIcon());
            vo.setSort(group.getSort());
            Long attrGroupId = vo.getAttrGroupId();
            LambdaQueryWrapper<AttrAttrgroupRelationEntity> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId);
            List<AttrAttrgroupRelationEntity> relations = relationService.list(queryWrapper1);
            List<Attr> attrs = new ArrayList<>();
            for (AttrAttrgroupRelationEntity rela : relations){
                Long attrId = rela.getAttrId();
                AttrEntity attr = attrService.getById(attrId);
                Attr attrVo = new Attr();
                BeanUtils.copyProperties(attr, attrVo);
                attrs.add(attrVo);
            }
            vo.setAttrs(attrs);
            vos.add(vo);
        }
        return R.ok().put("data", vos);
    }
}