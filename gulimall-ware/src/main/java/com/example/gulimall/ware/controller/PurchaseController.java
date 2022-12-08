package com.example.gulimall.ware.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.constant.WareConstant;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.entity.PurchaseDetailEntity;
import com.example.gulimall.ware.service.PurchaseDetailService;
import com.example.gulimall.ware.vo.PurchaseDoneVo;
import com.example.gulimall.ware.vo.PurchaseMergeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.ware.entity.PurchaseEntity;
import com.example.gulimall.ware.service.PurchaseService;




/**
 * 采购信息
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:29:57
 */
@RestController
@RequestMapping("/ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;


    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        return purchaseService.getList(params);
    }

    /**
     * 完成采购
     * @return
     */
    @PostMapping("/done")
    public R done(@RequestBody PurchaseDoneVo doneVo){
        return purchaseService.done(doneVo);
    }

    /**
     * 领取采购单
     * @param ids
     * @return
     */
    @PostMapping("/received")
    public R received(@RequestBody Long[] ids){
        return purchaseService.received(ids);
    }

    /**
     * 合并采购需求
     * @param mergeVo
     * @return
     */
    @PostMapping("/merge")
    public R merge(@RequestBody PurchaseMergeVo mergeVo){
        return purchaseService.merge(mergeVo);
    }

    /**
     * 获取采购单列表
     * @return
     */
    @GetMapping("/unreceive/list")
    public R getUnreceiveList(){
        LambdaQueryWrapper<PurchaseEntity> queryWrapper = new LambdaQueryWrapper<>();
        //返回状态为0,1的采购单
        queryWrapper.eq(PurchaseEntity::getStatus, WareConstant.PurchaseStatusEnum.CREATED.getCode()).or()
                .eq(PurchaseEntity::getStatus, WareConstant.PurchaseStatusEnum.ASSIGNED.getCode());
        Page<PurchaseEntity> pageInfo = new Page<>();
        purchaseService.page(pageInfo, queryWrapper);
        return R.ok().put("page", new PageUtils(pageInfo));
    }

    @PostMapping("/save")
    public R save(@RequestBody PurchaseEntity purchase){
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());
        purchaseService.save(purchase);
        return R.ok();
    }

    @GetMapping("/info/{id}")
    public R getInfo(@PathVariable("id") Long id){
        PurchaseEntity purchase = purchaseService.getById(id);
        return R.ok().put("purchase", purchase);
    }

    @PostMapping("/update")
    public R update(@RequestBody PurchaseEntity purchase){
        purchaseService.updateById(purchase);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        purchaseService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
