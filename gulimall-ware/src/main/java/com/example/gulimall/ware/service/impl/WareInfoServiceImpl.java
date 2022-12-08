package com.example.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.ware.dao.WareInfoDao;
import com.example.gulimall.ware.entity.WareInfoEntity;
import com.example.gulimall.ware.service.WareInfoService;
import org.springframework.util.StringUtils;


@Service
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {
    @Override
    public R getList(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        String key = (String) params.get("key");
        LambdaQueryWrapper<WareInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasLength(key)&& !"".equals(key)) {
            queryWrapper.like(WareInfoEntity::getName, key).or()
                    .eq(WareInfoEntity::getId, key);
        }
        IPage<WareInfoEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        PageUtils pageUtils = new PageUtils(pageInfo);
        return R.ok().put("page", pageUtils);
    }


}