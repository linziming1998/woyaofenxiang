package com.woyaofenxiang.controller;


import com.woyaofenxiang.entity.Chat;
import com.woyaofenxiang.util.Resp;
import com.woyaofenxiang.util.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-29
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    WebSocket webSocket;
    @PostMapping("/sendMessage")
    public void sendMessage(Chat chat) throws IOException, EncodeException {
        Resp resp=new Resp();
        chat.setCtime(LocalDateTime.now());
        resp.setData(chat);
        resp.setMessage("Chat");
        this.webSocket.sendMessage(chat.getCto(),resp);
    }
}

