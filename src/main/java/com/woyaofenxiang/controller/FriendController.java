package com.woyaofenxiang.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woyaofenxiang.entity.Chat;
import com.woyaofenxiang.entity.Friend;
import com.woyaofenxiang.entity.Shunjian;
import com.woyaofenxiang.service.FriendService;
import com.woyaofenxiang.util.Resp;
import com.woyaofenxiang.util.Uuid;
import com.woyaofenxiang.util.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-05
 */
@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    FriendService friendService;
    @Autowired
    WebSocket webSocket;
    @PostMapping("/addFriend")
    @Transactional(rollbackFor = Exception.class)
    public Resp addFriend(String mid,String uid) throws IOException, EncodeException {
        Resp resp=new Resp();
        Friend friend=new Friend();
        friend.setFid(Uuid.getUuid());
        friend.setFtime(LocalDateTime.now());
        friend.setMid(mid);
        friend.setUid(uid);
        Friend friend1=new Friend();
        friend1.setFid(Uuid.getUuid());
        friend1.setFtime(LocalDateTime.now());
        friend1.setMid(uid);
        friend1.setUid(mid);
        QueryWrapper<Friend>friendQueryWrapper=new QueryWrapper<>();
        friendQueryWrapper.eq("mid",mid);
        friendQueryWrapper.eq("uid",uid);
        if(this.friendService.getOne(friendQueryWrapper)!=null){
            resp.setCode(10017);
            resp.setMessage("该好友已存在");
        }
        else if(friend.insert()&&friend1.insert()){
            resp.setCode(10015);
            resp.setMessage("添加好友成功");
            Resp resp1=new Resp();
            Chat chat=new Chat();
            chat.setCfrom(uid);
            chat.setCtime(LocalDateTime.now());
            chat.setCtype("t");
            chat.setCto(mid);
            chat.setCmessage("好友添加成功，我们来聊天吧");
            resp1.setData(chat);
            resp1.setMessage("Chat");
            this.webSocket.sendMessage(chat.getCto(),resp1);
            chat.setCto(uid);
            chat.setCfrom(mid);
            resp1.setData(chat);
            this.webSocket.sendMessage(chat.getCto(),resp1);
        }else {
            resp.setCode(10016);
            resp.setMessage("添加好友失败");
        }
        return resp;
    }
    @GetMapping("/getFriendList")
    public List<Map<String, Object>> getFriendList(String mid){
        return this.friendService.getFriendList(mid);
    }
    @GetMapping("/getFriendInfo")
    public List<Map<String, Object>> getFriendInfo(String mid,String uid){
        return this.friendService.getFriendInfo(uid,mid);
    }
}

