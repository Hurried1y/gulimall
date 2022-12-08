package com.example.gulimall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.member.entity.MemberEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.member.dao.MemberLevelDao;
import com.example.gulimall.member.entity.MemberLevelEntity;
import com.example.gulimall.member.service.MemberLevelService;
import org.springframework.util.StringUtils;


@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelDao, MemberLevelEntity> implements MemberLevelService {
    @Override
    public R getList(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        String key = (String) params.get("key");
        LambdaQueryWrapper<MemberLevelEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasLength(key)&& !"".equals(key)) {
            queryWrapper.like(MemberLevelEntity::getName, key).or()
                    .eq(MemberLevelEntity::getId, key);
        }
        Page<MemberLevelEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        return R.ok().put("page", new PageUtils(pageInfo));
    }
}