package com.example.gulimall.ware.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.example.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.ware.entity.WareOrderTaskEntity;
import com.example.gulimall.ware.service.WareOrderTaskService;




/**
 * 库存工作单
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:29:57
 */
@RestController
@RequestMapping("/ware/wareordertask")
public class WareOrderTaskController {
    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        return wareOrderTaskService.getList(params);
    }

    @PostMapping("/save")
    public R save(@RequestBody WareOrderTaskEntity wareOrderTask){
        wareOrderTask.setCreateTime(new Date());
        wareOrderTaskService.save(wareOrderTask);
        return R.ok();
    }

    @GetMapping("/info/{id}")
    public R getInfo(@PathVariable("id") Long id){
        WareOrderTaskEntity wareOrderTask = wareOrderTaskService.getById(id);
        return R.ok().put("wareOrderTask", wareOrderTask);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        wareOrderTaskService.removeById(ids);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody WareOrderTaskEntity wareOrderTask){
        wareOrderTaskService.updateById(wareOrderTask);
        return R.ok();
    }
}
