package com.example.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:25:30
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    R getList(Map<String, Object> params);
}

