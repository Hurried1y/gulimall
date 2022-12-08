package com.example.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.gulimall.common.utils.R;
import com.example.gulimall.member.feign.CouponFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.member.entity.MemberEntity;
import com.example.gulimall.member.service.MemberService;




/**
 * 会员
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:25:30
 */
@RestController
@RequestMapping("/member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        return memberService.getList(params);
    }

}
