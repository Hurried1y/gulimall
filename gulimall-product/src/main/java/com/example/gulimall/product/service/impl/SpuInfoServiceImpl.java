package com.example.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gulimall.common.to.MemberPriceTo;
import com.example.gulimall.common.to.SkuFullReductionTo;
import com.example.gulimall.common.to.SkuLadderTo;
import com.example.gulimall.common.to.SpuBoundTo;
import com.example.gulimall.common.to.es.SkuEsModel;
import com.example.gulimall.common.utils.PageUtils;
import com.example.gulimall.common.utils.R;
import com.example.gulimall.product.entity.*;
import com.example.gulimall.product.feign.CouponFeignService;
import com.example.gulimall.product.service.*;
import com.example.gulimall.product.vo.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Autowired
    private SpuImagesService spuImagesService;
    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private ProductAttrValueService productAttrValueService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private CouponFeignService couponFeignService;

    @Override
    public R up(Long spuId) {
        //查出当前spuId对应的所有sku信息、品牌名称
        LambdaQueryWrapper<SkuInfoEntity> skuQuery = new LambdaQueryWrapper<>();
        skuQuery.eq(SkuInfoEntity::getSpuId, spuId);
        List<SkuInfoEntity> skus = skuInfoService.list(skuQuery);
        //封装每个sku的信息
        List<SkuEsModel> products = skus.stream().map(sku->{
            //组装需要的数据
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, skuEsModel);
            return skuEsModel;
        }).collect(Collectors.toList());

        SpuInfoEntity spuInfo = getById(spuId);


        spuInfo.setPublishStatus(1);
        updateById(spuInfo);
        return R.ok();
    }

    @Override
    public R getSpuList(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        Long catelogId = 0L;
        Long brandId = 0L;
        Integer status = null;
        if(params.get("catelogId") != null) {
            catelogId = Long.parseLong((String) params.get("catelogId"));
        }
        if(params.get("brandId") != null) {
            brandId = Long.parseLong(params.get("brandId").toString());
        }
        if(params.get("status") != null){
            status = Integer.parseInt(params.get("status").toString());
        }
        String key = (String) params.get("key");

        Page<SpuInfoEntity> pageInfo = new Page<>(page, limit);
        LambdaQueryWrapper<SpuInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasLength(key)&& !"".equals(key)) {
            queryWrapper.like(SpuInfoEntity::getSpuName, key).or()
                    .eq(SpuInfoEntity::getId, key);
        }
        queryWrapper.eq(catelogId!=0L, SpuInfoEntity::getCatalogId, catelogId);
        queryWrapper.eq(brandId!=0L, SpuInfoEntity::getBrandId, brandId);
        queryWrapper.eq(status!=null, SpuInfoEntity::getPublishStatus, status);
        page(pageInfo, queryWrapper);
        return R.ok().put("page", new PageUtils(pageInfo));
    }

    @Transactional
    @Override
    public R saveSpu(SpuInfoVo spuInfoVo) {
        //保存spu信息
        // - spu的基本信息，pms_spu_info
        SpuInfoEntity spuInfo = new SpuInfoEntity();
        spuInfo.setCreateTime(new Date());
        spuInfo.setUpdateTime(new Date());
        BeanUtils.copyProperties(spuInfoVo, spuInfo);
        save(spuInfo);
        Long spuId = spuInfo.getId();

        // - spu的图片信息,pms_spu_images
        List<String> imgs = spuInfoVo.getImages();
        List<SpuImagesEntity> imgEntities = new ArrayList<>();
        for (String img : imgs){
            SpuImagesEntity imagesEntity = new SpuImagesEntity();
            imagesEntity.setSpuId(spuId);
            imagesEntity.setImgUrl(img);
            imgEntities.add(imagesEntity);
        }
        spuImagesService.saveBatch(imgEntities);

        // - spu的decript信息,pms_spu_info_desc
        List<String> decript = spuInfoVo.getDecript();
        List<SpuInfoDescEntity> descEntities = new ArrayList<>();
        BeanUtils.copyProperties(decript, descEntities);
        for (SpuInfoDescEntity desc : descEntities){
            desc.setSpuId(spuId);
        }
        spuInfoDescService.saveBatch(descEntities);

        // - spu的规格参数,pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuInfoVo.getBaseAttrs();
        List<ProductAttrValueEntity> attrValues = baseAttrs.stream().map((attr) -> {
            ProductAttrValueEntity attrValue = new ProductAttrValueEntity();
            AttrEntity attrEntity = attrService.getById(attr.getAttrId());
            attrValue.setAttrName(attrEntity.getAttrName());
            attrValue.setAttrValue(attr.getAttrValues());
            attrValue.setQuickShow(attr.getShowDesc());
            attrValue.setAttrId(attr.getAttrId());
            attrValue.setSpuId(spuId);
            return attrValue;
        }).collect(Collectors.toList());
        productAttrValueService.saveBatch(attrValues);

        // - spu的积分信息,sms_spu_bounds
        Bounds bounds = spuInfoVo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds, spuBoundTo);
        couponFeignService.saveSpuBounds(spuBoundTo);

        //保存当前spu对应的所有sku信息
        List<Skus> skus = spuInfoVo.getSkus();
        if(skus!=null && skus.size()>0){
            List<SkuLadderTo> skuLadderTos = new ArrayList<>();
            List<SkuFullReductionTo> fullReductionTos = new ArrayList<>();
            List<MemberPriceTo> priceTos = new ArrayList<>();

            for (Skus sku : skus) {
                // - sku的基本信息,pms_sku_info
                SkuInfoEntity skuInfo = new SkuInfoEntity();
                BeanUtils.copyProperties(sku, skuInfo);
                List<Images> skuImages = sku.getImages();
                String defaultImg = "";
                for (int i = 0; i < skuImages.size(); i++) {
                    if(skuImages.get(i).getDefaultImg() == 1){
                        defaultImg = skuImages.get(i).getImgUrl();
                    }
                }
                skuInfo.setSkuDefaultImg(defaultImg);
                skuInfo.setSpuId(spuId);
                skuInfo.setBrandId(spuInfo.getBrandId());
                skuInfo.setCatalogId(spuInfo.getCatalogId());
                skuInfo.setSaleCount(0L);
                skuInfoService.save(skuInfo);
                Long skuId = skuInfo.getSkuId();

                // - sku的图片信息,pms_sku_images
                List<SkuImagesEntity> skuImgs = skuImages.stream().map((img) -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    BeanUtils.copyProperties(img, skuImagesEntity);
                    return skuImagesEntity;
                }).filter(img->{
                    return StringUtils.hasLength(img.getImgUrl());
                }).collect(Collectors.toList());
                skuImagesService.saveBatch(skuImgs);

                // - sku的销售属性信息,pms_sku_sale_attr_value
                List<SkuSaleAttrValueEntity> saleAttrValue = sku.getAttr().stream().map((item) -> {
                    SkuSaleAttrValueEntity skuAttr = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(item, skuAttr);
                    skuAttr.setSkuId(skuId);
                    return skuAttr;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(saleAttrValue);

                // - sku的优惠信息,sms_sku_ladder
                SkuLadderTo skuLadderTo = new SkuLadderTo();
                BeanUtils.copyProperties(sku, skuLadderTo);
                skuLadderTo.setSkuId(skuId);
                skuLadderTos.add(skuLadderTo);

                // - sku的满减信息,sms_sku_full_reduction
                SkuFullReductionTo fullReductionTo = new SkuFullReductionTo();
                BeanUtils.copyProperties(sku, fullReductionTo);
                fullReductionTo.setSkuId(skuId);
                fullReductionTos.add(fullReductionTo);

                // - sku的会员价格信息,sms_member_price
                for(MemberPrice item : sku.getMemberPrice()){
                    MemberPriceTo memberPriceTo = new MemberPriceTo();
                    memberPriceTo.setMemberPrice(item.getPrice());
                    memberPriceTo.setMemberLevelId(item.getId());
                    memberPriceTo.setSkuId(skuId);
                    memberPriceTo.setMemberLevelName(item.getName());
                    priceTos.add(memberPriceTo);
                }
            }
            if(!skuLadderTos.isEmpty()){
                couponFeignService.saveSkuLadders(skuLadderTos);
            }
            if(!fullReductionTos.isEmpty()){
                couponFeignService.saveSkuFullReduction(fullReductionTos);
            }
            if(!priceTos.isEmpty()){
                couponFeignService.saveMemberPrice(priceTos);
            }

        }

        return R.ok();
    }
}