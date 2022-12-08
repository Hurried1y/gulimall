package com.example.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.gulimall.common.utils.R;
import com.example.gulimall.member.vo.MemberLevelVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.gulimall.member.entity.MemberLevelEntity;
import com.example.gulimall.member.service.MemberLevelService;




/**
 * 会员等级
 *
 * @author Hurried1y
 * @email 3049478157@qq.com
 * @date 2022-11-14 08:25:30
 */
@RestController
@RequestMapping("/member/memberlevel")
public class MemberLevelController {
    @Autowired
    private MemberLevelService memberLevelService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        return memberLevelService.getList(params);
    }

    @PostMapping("/save")
    public R save(@RequestBody MemberLevelVo memberLevelVo){
        MemberLevelEntity memberLevelEntity = new MemberLevelEntity();
        BeanUtils.copyProperties(memberLevelVo, memberLevelEntity);
        memberLevelService.save(memberLevelEntity);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        memberLevelService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @GetMapping("/info/{id}")
    public R getInfo(@PathVariable("id") Long id){
        MemberLevelEntity levelEntity = memberLevelService.getById(id);
        return R.ok().put("memberLevel", levelEntity);
    }

    @PostMapping("/update")
    public R update(@RequestBody MemberLevelEntity memberLevelEntity){
        memberLevelService.updateById(memberLevelEntity);
        return R.ok();
    }
}
