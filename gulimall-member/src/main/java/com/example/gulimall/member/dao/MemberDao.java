package com.example.gulimall.member.dao;

import com.example.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:25:30
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
