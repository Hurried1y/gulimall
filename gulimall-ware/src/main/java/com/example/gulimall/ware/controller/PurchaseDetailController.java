package com.example.gulimall.ware.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import com.example.gulimall.common.to.SkuInfoTo;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.ware.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.ware.entity.PurchaseDetailEntity;
import com.example.gulimall.ware.service.PurchaseDetailService;




/**
 * 
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:29:57
 */
@RestController
@RequestMapping("/ware/purchasedetail")
public class PurchaseDetailController {
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    @Autowired
    private ProductFeignService productFeignService;

    @GetMapping("/list")
    public R getList(@RequestParam Map<String, Object> params){
        return purchaseDetailService.getList(params);
    }

    @PostMapping("/save")
    public R save(@RequestBody PurchaseDetailEntity purchaseDetail){
        Long skuId = purchaseDetail.getSkuId();
        R data = productFeignService.getSkuPrice(skuId);
        String str = data.get("price").toString();
        Long price = Long.parseLong(str.substring(0, str.lastIndexOf('.')));
        purchaseDetail.setSkuPrice(BigDecimal.valueOf(price*purchaseDetail.getSkuNum()));
//        R data = productFeignService.getSkuInfo(skuId);
//        SkuInfoTo sku = (SkuInfoTo) data.get("sku");
//        BigDecimal price = sku.getPrice();
//        price = price.multiply(BigDecimal.valueOf(purchaseDetail.getSkuNum()));
        purchaseDetailService.save(purchaseDetail);
        return R.ok();
    }

    @GetMapping("/info/{id}")
    public R getInfo(@PathVariable("id") Long id){
        PurchaseDetailEntity purchase = purchaseDetailService.getById(id);
        return R.ok().put("purchaseDetail", purchase);
    }

    @PostMapping("/update")
    public R update(@RequestBody PurchaseDetailEntity purchase){
        purchaseDetailService.updateById(purchase);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        purchaseDetailService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
