package com.woyaofenxiang.util;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.woyaofenxiang.entity.Chat;
import com.woyaofenxiang.entity.Jifen;
import com.woyaofenxiang.entity.Shunjian;
import com.woyaofenxiang.entity.Userinfo;
import com.woyaofenxiang.service.JifenService;
import com.woyaofenxiang.service.ShunjianService;
import com.woyaofenxiang.service.UserinfoService;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author linziming
 * @create 2020-04-27 10:08
 */
@Component
@EnableScheduling
public class CheckTask {
    @Autowired
    ShunjianService shunjianService;
    @Autowired
    JifenService jifenService;
    @Autowired
    WebSocket webSocket;
    @Autowired
    UserinfoService userinfoService;

    @Scheduled(fixedDelay = 1000 * 60)
    public void checktask() throws IOException, EncodeException {
        QueryWrapper<Shunjian> shunjianQueryWrapper = new QueryWrapper<>();
        shunjianQueryWrapper.eq("stype", "task");
        shunjianQueryWrapper.in("ttype", "2", "1");
        shunjianQueryWrapper.le("endtime", new Date());
//        System.out.println(LocalDateTime.now().toString());
        List<Shunjian> shunjianList = this.shunjianService.list(shunjianQueryWrapper);
        for (int i = 0; i < shunjianList.size(); i++) {
            Shunjian shunjian = shunjianList.get(i);
            shunjian.setTtype("4");
            shunjian.updateById();
            Jifen jifen = this.jifenService.getById(shunjian.getSuid());
            jifen.setJnum(jifen.getJnum() + shunjian.getSjifen());
            jifen.updateById();
            Resp resp1 = new Resp();
            Chat chat = new Chat();
            chat.setCfrom(shunjian.getSuid());
            chat.setCtime(LocalDateTime.now());
            chat.setCtype("j");
            chat.setCto(shunjian.getSuid());
            Dianzantext dianzantext = new Dianzantext();
            dianzantext.setUserinfo(this.userinfoService.getById(shunjian.getSuid()));
            dianzantext.setShunjian(shunjian);
            chat.setCmessage(JSONArray.toJSONString(dianzantext));
            resp1.setData(chat);
            resp1.setMessage("Task");
            this.webSocket.sendMessage(chat.getCto(), resp1);

            if(shunjian.getTakeid()!=null){
                chat.setCto(shunjian.getTakeid());
                resp1.setData(chat);
                this.webSocket.sendMessage(chat.getCto(), resp1);
            }
        }
    }
}

class Dianzantext {
    Userinfo userinfo;
    Shunjian shunjian;

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public Shunjian getShunjian() {
        return shunjian;
    }

    public void setShunjian(Shunjian shunjian) {
        this.shunjian = shunjian;
    }
}
