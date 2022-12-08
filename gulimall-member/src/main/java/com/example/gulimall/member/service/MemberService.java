package com.example.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:25:30
 */
public interface MemberService extends IService<MemberEntity> {

    R getList(Map<String, Object> params);
}

