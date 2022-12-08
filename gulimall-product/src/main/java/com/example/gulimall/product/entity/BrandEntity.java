package com.example.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.example.gulimall.common.validator.group.AddGroup;
import com.example.gulimall.common.validator.group.UpdateGroup;
import com.example.gulimall.common.validator.annotation.ListValue;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-13 21:25:19
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 * 品牌id需要分组校验功能，修改和新增时用不同的校验规则
	 */
	@NotNull(message = "修改数据必须指定brandId", groups = {UpdateGroup.class})
	@Null(message = "新增不能指定brandId", groups = {AddGroup.class})
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名不能为空", groups = {UpdateGroup.class, AddGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message = "logo地址不合法", groups = {UpdateGroup.class, AddGroup.class})
	@NotEmpty(groups = {AddGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValue(value = {0, 1}, groups = {AddGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@Pattern(regexp = "^[a-zA-z]$", message = "检索首字母不合法", groups = {UpdateGroup.class, AddGroup.class})
	@NotEmpty(groups = {AddGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(groups = {AddGroup.class})
	@Min(value = 0, message = "排序字段必须为正整数", groups = {UpdateGroup.class, AddGroup.class})
	private Integer sort;

}
