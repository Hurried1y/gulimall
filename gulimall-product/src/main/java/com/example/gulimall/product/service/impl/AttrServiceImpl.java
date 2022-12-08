package com.example.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.constant.ProductConstant;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.example.gulimall.product.entity.*;
import com.example.gulimall.product.service.*;
import com.example.gulimall.product.vo.AttrRespVo;
import com.example.gulimall.product.vo.AttrVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.product.dao.AttrDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Slf4j
@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Override
    public R getAttrList(Map<String, Object> params, Long catelogId, Integer attrType) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasLength(key)&& !"".equals(key)) {
            queryWrapper.like(AttrEntity::getAttrName, key).or()
                    .eq(AttrEntity::getAttrId, key);
        }
        queryWrapper.eq(AttrEntity::getAttrType, attrType);
        queryWrapper.eq(catelogId!=0, AttrEntity::getCatelogId, catelogId);

        IPage<AttrEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        PageUtils pageUtils = new PageUtils(pageInfo);
        List<AttrEntity> attrEntityList = pageInfo.getRecords();
        List<AttrRespVo> list = attrEntityList.stream().map((item) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(item, attrRespVo);
            AttrAttrgroupRelationEntity relation = attrAttrgroupRelationService.getOne(
                    new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attrRespVo.getAttrId()));
            if(relation!=null) {
                Long attrGroupId = relation.getAttrGroupId();
                AttrGroupEntity attrGroupEntity = attrGroupService.getById(attrGroupId);
                if (attrGroupEntity != null) {
                    String groupName = attrGroupEntity.getAttrGroupName();
                    attrRespVo.setGroupName(groupName);
                }
            }
            CategoryEntity categoryEntity = categoryService.getById(attrRespVo.getCatelogId());
            if(categoryEntity!=null) {
                String categoryName = categoryEntity.getName();
                attrRespVo.setCatelogName(categoryName);
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(list);
        return R.ok().put("page", pageUtils);
    }

    @Transactional
    @Override
    public R saveAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        save(attrEntity);
        if(attrVo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attrVo.getAttrGroupId()!=null) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
        }
        return R.ok();
    }

    @Override
    public R getInfo(Long attrId) {
        AttrEntity attrEntity = getById(attrId);
        AttrRespVo attr = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity, attr);
        //获取完整菜单分类
        CategoryEntity categoryEntity = categoryService.getById(attrEntity.getCatelogId());
        if(categoryEntity!=null) {
            List<Long> catelogPath = categoryService.getCatelogPath(attrEntity.getCatelogId());
            attr.setCatelogPath(catelogPath);
        }
        //获取分组信息
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationService.getById(attrId);
        if(attrAttrgroupRelationEntity!=null) {
            Long attrGroupId = attrAttrgroupRelationEntity.getAttrGroupId();
            attr.setAttrGroupId(attrGroupId);
        }
        return R.ok().put("attr", attr);
    }

    @Transactional
    @Override
    public R updateAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        Long attrId = attrEntity.getAttrId();
        Long attrGroupId = attrVo.getAttrGroupId();
        BeanUtils.copyProperties(attrVo, attrEntity);
        updateById(attrEntity);
        //修改分组关联信息
        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            LambdaQueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AttrAttrgroupRelationEntity::getAttrId, attrId);
            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationService.getOne(queryWrapper);
            if (relationEntity == null) {
                relationEntity = new AttrAttrgroupRelationEntity();
                relationEntity.setAttrId(attrId);
                relationEntity.setAttrGroupId(attrGroupId);
                attrAttrgroupRelationService.save(relationEntity);
            } else {
                relationEntity.setAttrGroupId(attrGroupId);
                attrAttrgroupRelationService.updateById(relationEntity);
            }
        }
        return R.ok();
    }

    //通过groupId获取attr
    @Override
    public R getAttrByAttrGroupId(Long attrGroupId) {
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId);
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationService.list(queryWrapper);
        List<AttrEntity> attrEntities = relationEntities.stream().map((item) -> {
            return getById(item.getAttrId());
        }).collect(Collectors.toList());
        return R.ok().put("data", attrEntities);
    }

    //获取为绑定分组的属性
    @Override
    public R getNoRelationAttrByAttrGruopId(Map<String, Object> params, Long attrGroupId) {
        //获取该分组所属的分类
        AttrGroupEntity groupEntity = attrGroupService.getById(attrGroupId);
        Long catelogId = groupEntity.getCatelogId();

        //获取当前已经绑定分组的attrId
        List<AttrAttrgroupRelationEntity> relationList = attrAttrgroupRelationService.list();
        List<Long> noAttrIds = relationList.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        log.info("noAttrIds:{}", noAttrIds);

        //根据分类id获取属性集合
        LambdaQueryWrapper<AttrEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttrEntity::getCatelogId, catelogId);
        queryWrapper.eq(AttrEntity::getAttrType, 1); //必须为基本属性
        queryWrapper.notIn(noAttrIds.size()>0, AttrEntity::getAttrId, noAttrIds);

        //准备page
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        String key = (String) params.get("key");
        if(StringUtils.hasLength(key)&& !"".equals(key)) {
            queryWrapper.like(AttrEntity::getAttrName, key).or()
                    .eq(AttrEntity::getAttrId, key);
        }

        IPage<AttrEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        PageUtils pageUtils = new PageUtils(pageInfo);
        return R.ok().put("page", pageUtils);
    }

    @Override
    public R getListForSpu(Long spuId) {
        LambdaQueryWrapper<ProductAttrValueEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductAttrValueEntity::getSpuId, spuId);
        List<ProductAttrValueEntity> list = productAttrValueService.list(queryWrapper);
        return R.ok().put("data", list);
    }

    @Override
    public R updateAttrValue(Long id, List<ProductAttrValueEntity> attrValues) {
        attrValues.forEach(item->{
            Long attrId = item.getAttrId();
            LambdaQueryWrapper<ProductAttrValueEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProductAttrValueEntity::getAttrId, attrId);
            queryWrapper.eq(ProductAttrValueEntity::getSpuId, id);
            ProductAttrValueEntity value = productAttrValueService.getOne(queryWrapper);
            item.setId(value.getId());
        });
        productAttrValueService.updateBatchById(attrValues);
        return R.ok();
    }
}