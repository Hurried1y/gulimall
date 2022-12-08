package com.example.gulimall.order.dao;

import com.example.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:27:46
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
