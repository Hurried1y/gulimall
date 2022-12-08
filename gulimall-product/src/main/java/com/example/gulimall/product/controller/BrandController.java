package com.example.gulimall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.common.validator.group.AddGroup;
import com.example.gulimall.common.validator.group.UpdateGroup;
import com.example.gulimall.product.entity.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.product.entity.BrandEntity;
import com.example.gulimall.product.service.BrandService;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:19
 */
@RestController
@RequestMapping("/product/brand")
@Slf4j
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    public R getBrandList(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);
        return R.ok().put("page", page);
    }

    @PostMapping("/delete")
    public R deleteBrandList(@RequestBody Long[] ids){
        brandService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


    @GetMapping("/info/{brandId}")
    public R getBrandInfo(@PathVariable Long brandId){
        BrandEntity brandEntity = brandService.getById(brandId);
        return R.ok().put("brand", brandEntity);
    }

    /**
     * @Valid校验注解，表示这个参数需要校验
     * @Validated由springboot实现的校验注解，可以实现分组校验
     * BindingResult，表示前一个@Valid注解的数据的校验结果
     *   这里采用统一异常处理，将异常抛出交给统一异常处理器处理
     * @param brandEntity
     * @return
     */
    @PostMapping("/save")
    public R addBrand(@Validated({AddGroup.class}) @RequestBody BrandEntity brandEntity/*, BindingResult result*/){
//        if(result.hasErrors()){
//            //获取校验结果
//            Map<String, String> map = new HashMap<>();
//            result.getFieldErrors().forEach((item)->{
//                //错误信息
//                String message = item.getDefaultMessage();
//                //获取错误的属性名
//                String field = item.getField();
//                map.put(field, message);
//            });
//            return R.error(400, "提交的数据不合法").put("data", map);
//        }
        brandService.save(brandEntity);
        return R.ok();
    }

    @PostMapping("/update")
    public R updateBrand(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brandEntity){
        return brandService.updateBrand(brandEntity);
    }

    @PostMapping("/updateBrandStatus")
    public R updateBrandStatus(@RequestBody BrandEntity brandEntity){
        brandService.updateById(brandEntity);
        return R.ok();
    }


}
