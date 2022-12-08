package com.example.gulimall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.member.dao.MemberDao;
import com.example.gulimall.member.entity.MemberEntity;
import com.example.gulimall.member.service.MemberService;
import org.springframework.util.StringUtils;


@Service
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {
    @Override
    public R getList(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        String key = params.get("key").toString();
        LambdaQueryWrapper<MemberEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasLength(key)&& !"".equals(key)) {
            queryWrapper.like(MemberEntity::getUsername, key).or()
                    .eq(MemberEntity::getId, key);
        }
        Page<MemberEntity> pageInfo = new Page<>(page, limit);
        page(pageInfo, queryWrapper);
        return R.ok().put("page", new PageUtils(pageInfo));
    }
}