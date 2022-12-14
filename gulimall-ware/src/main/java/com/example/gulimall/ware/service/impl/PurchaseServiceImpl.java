package com.example.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.constant.WareConstant;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.entity.PurchaseDetailEntity;
import com.example.gulimall.ware.service.PurchaseDetailService;
import com.example.gulimall.ware.service.WareSkuService;
import com.example.gulimall.ware.vo.PurchaseDoneVo;
import com.example.gulimall.ware.vo.PurchaseMergeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.ware.dao.PurchaseDao;
import com.example.gulimall.ware.entity.PurchaseEntity;
import com.example.gulimall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    @Autowired
    private WareSkuService wareSkuService;


    @Override
    public R getList(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        String key = (String) params.get("key");
        Integer status = null;
        if(params.get("status") != null && !"".equals(params.get("status").toString())){
            status = Integer.parseInt(params.get("wareId").toString());
        }
        LambdaQueryWrapper<PurchaseEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(key), PurchaseEntity::getAssigneeName, key);
        queryWrapper.eq(status!=null, PurchaseEntity::getStatus, status);
        IPage<PurchaseEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        PageUtils pageUtils = new PageUtils(pageInfo);
        return R.ok().put("page", pageUtils);
    }

    @Override
    @Transactional
    public R merge(PurchaseMergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        Long[] items = mergeVo.getItems();
        BigDecimal amount = BigDecimal.valueOf(0);
        //??????????????????????????????????????????
        for (int i = 0; i < items.length; i++) {
            PurchaseDetailEntity detail = purchaseDetailService.getById(items[i]);
            if (detail.getStatus()!=WareConstant.PurchaseDetailStatusEnum.CREATED.getCode()){
                throw new UnsupportedOperationException();
            }
            detail.setPurchaseId(purchaseId);
            detail.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            amount = amount.add(detail.getSkuPrice());
            purchaseDetailService.updateById(detail);
        }
        //????????????????????????
        PurchaseEntity purchase = getById(purchaseId);
        purchase.setStatus(WareConstant.PurchaseStatusEnum.ASSIGNED.getCode());
        purchase.setAmount(amount);
        updateById(purchase);
        return R.ok();
    }

    @Override
    @Transactional
    public R received(Long[] ids) {
        //?????????????????????
        List<PurchaseEntity> purchaseEntities = Arrays.stream(ids).map(this::getById).filter(item -> {
            PurchaseEntity purchase = getById(item);
            if (purchase.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() ||
                    purchase.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode())
                return true;
            return false;
        }).peek(item -> {
            item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
            item.setUpdateTime(new Date());
        }).collect(Collectors.toList());
        updateBatchById(purchaseEntities);

        //????????????????????????
        purchaseEntities.forEach(item->{
            purchaseDetailService.changeStatusByPurchaseId(item.getId());
        });
        return null;
    }

    @Override
    @Transactional
    public R done(PurchaseDoneVo doneVo) {
        //????????????????????????
        List<PurchaseDetailEntity> details = new ArrayList<>();
        AtomicReference<Boolean> flag = new AtomicReference<>(true);
        doneVo.getItems().forEach(item->{
            PurchaseDetailEntity detail = purchaseDetailService.getById(item.getItemId());
            if(item.getStatus()==WareConstant.PurchaseDetailStatusEnum.HASERROR.getCode()){
                flag.set(false);
                detail.setStatus(item.getStatus());
            }
            else {
                detail.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());
                //??????????????????????????????
                wareSkuService.addStock(detail.getSkuId(), detail.getWareId(), detail.getSkuNum());
            }
            detail.setId(item.getItemId());
            details.add(detail);
        });
        purchaseDetailService.updateBatchById(details);
        //????????????????????????
        Long purchaseId = doneVo.getId();
        PurchaseEntity purchase = getById(purchaseId);
        purchase.setStatus(flag.get()?WareConstant.PurchaseStatusEnum.FINISH.getCode():WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        purchase.setUpdateTime(new Date());

        return R.ok();
    }
}