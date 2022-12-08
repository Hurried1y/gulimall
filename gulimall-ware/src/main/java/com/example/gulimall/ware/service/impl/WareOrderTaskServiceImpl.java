package com.example.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.entity.WareSkuEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.ware.dao.WareOrderTaskDao;
import com.example.gulimall.ware.entity.WareOrderTaskEntity;
import com.example.gulimall.ware.service.WareOrderTaskService;
import org.springframework.util.StringUtils;


@Service
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskDao, WareOrderTaskEntity> implements WareOrderTaskService {
    @Override
    public R getList(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        String key = (String) params.get("key");

        LambdaQueryWrapper<WareOrderTaskEntity> queryWrapper = new LambdaQueryWrapper<>();
        IPage<WareOrderTaskEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        PageUtils pageUtils = new PageUtils(pageInfo);
        return R.ok().put("page", pageUtils);
    }
}