package com.woyaofenxiang.controller;


import com.woyaofenxiang.entity.Jifen;
import com.woyaofenxiang.service.JifenService;
import com.woyaofenxiang.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-18
 */
@RestController
@RequestMapping("/jifen")
public class JifenController {
    @Autowired
    JifenService jifenService;

    @PostMapping("/qiandao")
    public Resp qiandao(String uid){
        Resp resp=new Resp();
        Jifen jifen=this.jifenService.getById(uid);
        if(jifen.getJtime().equals(LocalDate.now())){
            resp.setMessage("今天已签到");
        }else{
            jifen.setJnum(jifen.getJnum()+10);
            jifen.setJtime(LocalDate.now());
            jifen.updateById();
            resp.setCode(10020);
            resp.setMessage("签到成功，积分加10");
            resp.setData(jifen.getJnum());
        }
        return resp;
    };
}

