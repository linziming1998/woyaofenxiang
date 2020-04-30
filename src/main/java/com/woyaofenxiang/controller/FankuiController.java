package com.woyaofenxiang.controller;


import com.woyaofenxiang.entity.Fankui;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-19
 */
@RestController
@RequestMapping("/fankui")
public class FankuiController {
    @PostMapping("/fankui")
    public String fankui(Fankui fankui){
        fankui.insert();
        return "反馈成功";
    }
}

