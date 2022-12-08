package com.example.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * User：Hurried1y
 * Date：2022/12/6
 * Description：
 */
@Data
public class PurchaseDoneVo {
    /**
     * 采购单id
     */
    private Long id;
    private List<Item> items;
}
