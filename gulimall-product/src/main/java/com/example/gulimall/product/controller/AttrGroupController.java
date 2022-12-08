package com.example.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.example.gulimall.product.service.AttrAttrgroupRelationService;
import com.example.gulimall.product.service.AttrService;
import com.example.gulimall.product.vo.AttrGroupRelationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.product.entity.AttrGroupEntity;
import com.example.gulimall.product.service.AttrGroupService;




/**
 * 属性分组
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:19
 */
@Slf4j
@RequestMapping("/product/attrgroup")
@RestController
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private AttrAttrgroupRelationService relationService;
    @Autowired
    private AttrService attrService;

    @GetMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId){
        return R.ok().put("page", attrGroupService.getAttrGroupList(catelogId, params));
    }

    @GetMapping("/info/{attrGroupId}")
    public R getAttrGroupInfo(@PathVariable("attrGroupId") Long attrGroupId){
        return attrGroupService.getAttrGroupInfo(attrGroupId);
    }

    @PostMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroupEntity){
        attrGroupService.save(attrGroupEntity);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroupEntity){
        attrGroupService.updateById(attrGroupEntity);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        attrGroupService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 获取属性分组的关联的所有属性
     * @param attrGroupId
     * @return
     */
    @GetMapping("/{attrGroupId}/attr/relation")
    public R getAttrByAttrGroupId(@PathVariable("attrGroupId") Long attrGroupId){
        return attrService.getAttrByAttrGroupId(attrGroupId);
    }

    /**
     * 获取属性分组里面还没有关联的本分类里面的其他基本属性，方便添加新的关联
     * @param params
     * @param attrGroupId
     * @return
     */
    @GetMapping("/{attrGroupId}/noattr/relation")
    public R getNoRelationAttrByAttrGruopId(@RequestParam Map<String, Object> params,
                                            @PathVariable("attrGroupId") Long attrGroupId){
        return attrService.getNoRelationAttrByAttrGruopId(params, attrGroupId);
    }

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody List<AttrGroupRelationVo> relationVos){
        return relationService.deleteRelations(relationVos);
    }

    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> relationVos){
        return relationService.addRelations(relationVos);
    }

    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrByCatelogId(@PathVariable("catelogId") Long catelogId){
        return attrGroupService.getAttrGroupWithAttrByCatelogId(catelogId);
    }
}
