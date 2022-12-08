package com.example.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:29:57
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    R getList(Map<String, Object> params);
}

