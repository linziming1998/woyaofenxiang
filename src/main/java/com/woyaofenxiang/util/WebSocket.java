package com.woyaofenxiang.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woyaofenxiang.configuration.ServerEncoder;
import com.woyaofenxiang.entity.Chat;
import com.woyaofenxiang.entity.User;
import com.woyaofenxiang.entity.Userinfo;
import com.woyaofenxiang.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author linziming
 * @create 2020-03-28 11:12
 */
@Component
// 你的WebSocket访问地址
@ServerEndpoint(value = "/webSocket/{uid}", encoders = {ServerEncoder.class})
public class WebSocket {
    private static ChatService chatService;
    private Session session;
    /**
     * 在线用户
     */
    private static Map<String, Session> webSocketMap = new ConcurrentHashMap<>();

    @Autowired
    public void setChatService(ChatService chatService) {
        WebSocket.chatService = chatService;
    }

    /**
     * 建立连接
     */
    @OnOpen
    public void onOpen(@PathParam("uid") String uid, Session session) throws IOException, EncodeException {
        this.session = session;
        webSocketMap.put(uid, session);
        System.out.println(uid + "用户已连接");
        Resp resp = new Resp();
        //离线聊天记录
        resp.setMessage("ChatList");
        this.sendMessage(uid, resp);
        //离线用户添加
        resp.setMessage("AddFriendList");
        this.sendMessage(uid, resp);
        //离线用户添加
        resp.setMessage("DianzanList");
        this.sendMessage(uid, resp);
        //离线用户添加
        resp.setMessage("PinglunList");
        this.sendMessage(uid, resp);
        //离线用户添加
        resp.setMessage("TaskList");
        this.sendMessage(uid, resp);
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose(@PathParam("uid") String uid, Session session) {
        webSocketMap.remove(uid);
        System.out.println(uid + "用户已断开");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接异常
     */
    @OnError
    public void sessionError(@PathParam("uid") String uid, Session session, Throwable throwable) {
        if (webSocketMap.get(uid) != null)
            webSocketMap.remove(uid);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("WebSocket连接发生异常，message:" + throwable.getMessage());
    }

    /**
     * 向某个用户发送信息
     */
    public void sendMessage(String to, Resp message) throws IOException, EncodeException {
        session = webSocketMap.get(to);
        //如果用户不在线且发送为聊天消息
        if (session == null && (message.getMessage().equals("Chat") ||
                message.getMessage().equals("AddFriend") ||
                message.getMessage().equals("Task")||
                message.getMessage().equals("Dianzan") ||
                message.getMessage().equals("Pinglun"))) {
            Chat chat = (Chat) message.getData();
            chat.insert();
        } else if (message.getMessage().equals("ChatList")) {
            QueryWrapper<Chat> chatQueryWrapper = new QueryWrapper<>();
            chatQueryWrapper.eq("cto", to);
            chatQueryWrapper.in("ctype", "i", "t");
            chatQueryWrapper.orderByAsc("ctime");
            List<Chat> chatList = chatService.list(chatQueryWrapper);
            if (chatList.size() != 0) {
                message.setData(chatList);
                this.session.getBasicRemote().sendObject(message);
                this.chatService.remove(chatQueryWrapper);
            }
        } else if (message.getMessage().equals("AddFriendList")) {
            QueryWrapper<Chat> chatQueryWrapper = new QueryWrapper<>();
            chatQueryWrapper.eq("cto", to);
            chatQueryWrapper.eq("ctype", "a");
            chatQueryWrapper.orderByDesc("ctime");
            List<Chat> chatList = chatService.list(chatQueryWrapper);
            if (chatList.size() != 0) {
                message.setData(chatList);
                this.session.getBasicRemote().sendObject(message);
                this.chatService.remove(chatQueryWrapper);
            }
        } else if (message.getMessage().equals("DianzanList")) {
            QueryWrapper<Chat> chatQueryWrapper = new QueryWrapper<>();
            chatQueryWrapper.eq("cto", to);
            chatQueryWrapper.eq("ctype", "d");
            chatQueryWrapper.orderByDesc("ctime");
            List<Chat> chatList = chatService.list(chatQueryWrapper);
            if (chatList.size() != 0) {
                message.setData(chatList);
                this.session.getBasicRemote().sendObject(message);
                this.chatService.remove(chatQueryWrapper);
            }
        } else if (message.getMessage().equals("PinglunList")) {
            QueryWrapper<Chat> chatQueryWrapper = new QueryWrapper<>();
            chatQueryWrapper.eq("cto", to);
            chatQueryWrapper.eq("ctype", "p");
            chatQueryWrapper.orderByDesc("ctime");
            List<Chat> chatList = chatService.list(chatQueryWrapper);
            if (chatList.size() != 0) {
                message.setData(chatList);
                this.session.getBasicRemote().sendObject(message);
                this.chatService.remove(chatQueryWrapper);
            }
        } else if (message.getMessage().equals("TaskList")) {
            QueryWrapper<Chat> chatQueryWrapper = new QueryWrapper<>();
            chatQueryWrapper.eq("cto", to);
            chatQueryWrapper.eq("ctype", "j");
            chatQueryWrapper.orderByDesc("ctime");
            List<Chat> chatList = chatService.list(chatQueryWrapper);
            if (chatList.size() != 0) {
                message.setData(chatList);
                this.session.getBasicRemote().sendObject(message);
                this.chatService.remove(chatQueryWrapper);
            }
        }
        else {
            session.getBasicRemote().sendObject(message);
        }
    }


}