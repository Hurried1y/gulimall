package com.example.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.constant.WareConstant;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.entity.WareInfoEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.ware.dao.PurchaseDetailDao;
import com.example.gulimall.ware.entity.PurchaseDetailEntity;
import com.example.gulimall.ware.service.PurchaseDetailService;
import org.springframework.util.StringUtils;


@Service
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {
    @Override
    public R getList(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        Integer status = null;
        Long wareId = 0L;
        if(params.get("status") != null && !"".equals(params.get("status").toString())){
            status = Integer.parseInt(params.get("status").toString());
        }
        if(params.get("wareId") != null && !"".equals(params.get("wareId").toString())){
            wareId = Long.parseLong(params.get("wareId").toString());
        }
        String key = (String) params.get("key");
        LambdaQueryWrapper<PurchaseDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(wareId!=0L, PurchaseDetailEntity::getWareId, wareId);
        queryWrapper.eq(status!=null, PurchaseDetailEntity::getStatus, status);
        if(StringUtils.hasLength(key)&& !"".equals(key)) {
            queryWrapper.like(PurchaseDetailEntity::getPurchaseId, key);
        }
        IPage<PurchaseDetailEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        PageUtils pageUtils = new PageUtils(pageInfo);
        return R.ok().put("page", pageUtils);
    }

    @Override
    public R changeStatusByPurchaseId(Long id) {
        LambdaQueryWrapper<PurchaseDetailEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseDetailEntity::getPurchaseId, id);
        List<PurchaseDetailEntity> list = list(queryWrapper);
        list.forEach(item->{
            item.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
        });
        updateBatchById(list);
        return R.ok();
    }
}