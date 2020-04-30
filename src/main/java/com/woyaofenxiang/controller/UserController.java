package com.woyaofenxiang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woyaofenxiang.entity.Chat;
import com.woyaofenxiang.entity.Jifen;
import com.woyaofenxiang.entity.User;
import com.woyaofenxiang.entity.Userinfo;
import com.woyaofenxiang.service.JifenService;
import com.woyaofenxiang.service.UserService;
import com.woyaofenxiang.service.UserinfoService;
import com.woyaofenxiang.util.Resp;
import com.woyaofenxiang.util.Util;
import com.woyaofenxiang.util.Uuid;
import com.woyaofenxiang.util.WebSocket;
import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-24
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserinfoService userinfoService;
    @Autowired
    WebSocket webSocket;
    @Autowired
    JifenService jifenService;

    @PostMapping("/login")
    public Resp login(User user){
        Resp resp=new Resp();
        QueryWrapper<User> wrapper=new QueryWrapper();
        wrapper.eq("uphone",user.getUphone());
        User s=this.userService.getOne(wrapper);
        if(s!=null){
            if(s.getUpassword().equals(user.getUpassword())){
                resp.setCode(10000);
                resp.setMessage("登录成功");
                Userinfo userinfo=userinfoService.getById(s.getUid());
                resp.setData(userinfo);
                return resp;
            }
            else {
                resp.setCode(10001);
                resp.setMessage("密码错误");
                return resp;
            }
        }else {
            resp.setCode(10002);
            resp.setMessage("账号不存在");
            return resp;
        }
    }
    @PostMapping("/register")
    public Resp register(User user){
        Resp resp=new Resp();
        QueryWrapper<User>wrapper=new QueryWrapper<>();
        wrapper.eq("uphone",user.getUphone());
        if(this.userService.getOne(wrapper)==null){
            user.setUid(Uuid.getUuid());
            if(user.insert()){
                Userinfo userinfo=new Userinfo();
                userinfo.setUid(user.getUid());
                userinfo.setUisnew("1");
                userinfo.setUregtime(LocalDate.now());
                userinfo.insert();
                Jifen jifen=new Jifen();
                jifen.setJuid(user.getUid());
                jifen.setJnum(120);
                jifen.setXinyufen(100);
                jifen.setJtime(LocalDate.now());
                jifen.insert();
                resp.setCode(10003);
                resp.setMessage("注册成功");
                return resp;
            }else {
                resp.setCode(10004);
                resp.setMessage("注册失败");
                return resp;
            }
        }else {
            resp.setCode(10005);
            resp.setMessage("该用户已注册过");
            return resp;
        }
    }
    @PostMapping("/reset")
    public Resp reset(User user){
        Resp resp=new Resp();
        QueryWrapper<User>wrapper=new QueryWrapper<>();
        wrapper.eq("uphone",user.getUphone());
        User u=this.userService.getOne(wrapper);
        if(u==null){
            resp.setCode(10002);
            resp.setMessage("账号不存在");
            return resp;
        }else {
            u.setUpassword(user.getUpassword());
            if(u.updateById()){
                resp.setCode(10006);
                resp.setMessage("重置密码成功");
                return resp;
            }else{
                resp.setCode(10007);
                resp.setMessage("重置密码失败");
                return resp;
            }
        }
    }
    //添加好友时搜索
    @GetMapping("/getjifen")
    public Jifen getjifen(String juid){
        return this.jifenService.getById(juid);
    }
//    获取当前的积分
    @GetMapping("/search")
    public Resp search(String num){
        Resp resp=new Resp();
        resp.setData(this.userService.getUserInfo(num));
        return resp;
    }
    @GetMapping("/searchInfo")
    public Resp searchInfo(String num){
        Resp resp=new Resp();
        resp.setData(this.userinfoService.getById(num));
        return resp;
    }
    //添加好友的请求
    @PostMapping("/sendAdd")
    public void sendAdd(Chat chat) throws IOException, EncodeException {
        Resp resp=new Resp();
        chat.setCtime(LocalDateTime.now());
        resp.setData(chat);
        switch (chat.getCtype()){
            case "a":resp.setMessage("AddFriend");break;
            default:resp.setMessage("Chat");
        }
        this.webSocket.sendMessage(chat.getCto(),resp);
    }
    //同意添加的请求
    @PostMapping("/addOk")
    public void addOk(){

    }
}

