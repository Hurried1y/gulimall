package com.example.gulimall.ware.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.service.impl.WareInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.ware.entity.WareInfoEntity;
import com.example.gulimall.ware.service.WareInfoService;




/**
 * 仓库信息
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:29:57
 */
@RestController
@RequestMapping("/ware/wareinfo")
public class WareInfoController {
    @Autowired
    private WareInfoService wareInfoService;

    @GetMapping("/list")
    public R getList(@RequestParam Map<String, Object> params){
        return wareInfoService.getList(params);
    }

    @PostMapping("/save")
    public R save(@RequestBody WareInfoEntity wareInfo){
        wareInfoService.save(wareInfo);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        wareInfoService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @GetMapping("/info/{id}")
    public R getInfo(@PathVariable("id") Long id){
        WareInfoEntity wareInfo = wareInfoService.getById(id);
        return R.ok().put("wareInfo", wareInfo);
    }

    @PostMapping("/update")
    public R update(@RequestBody WareInfoEntity wareInfo){
        wareInfoService.updateById(wareInfo);
        return R.ok();
    }


}
