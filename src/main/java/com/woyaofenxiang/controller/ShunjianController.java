package com.woyaofenxiang.controller;


import com.alibaba.druid.sql.visitor.functions.Now;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.woyaofenxiang.entity.*;
import com.woyaofenxiang.service.*;
import com.woyaofenxiang.util.Resp;
import com.woyaofenxiang.util.Uuid;
import com.woyaofenxiang.util.WebSocket;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.EncodeException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-24
 */
@RestController
@RequestMapping("/shunjian")
public class ShunjianController {
    @Value("${data.url}")
    String url;
    @Value("${server.port}")
    String port;
    @Autowired
    ShunjianService shunjianService;
    @Autowired
    FriendService friendService;
    @Autowired
    DianzanService dianzanService;
    @Autowired
    PinglunService pinglunService;
    @Autowired
    UserinfoService userinfoService;
    @Autowired
    GoumaiService goumaiService;
    @Autowired
    JifenService jifenService;
    @Autowired
    WebSocket webSocket;

    @PostMapping("/testDate")
    public void testDate(@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date date) {
        System.out.println(date.toString());
    }

    @PostMapping("/setdianzan")
    public void setdianzan(Dianzan dianzan) throws IOException, EncodeException {
        dianzan.setDtime(LocalDateTime.now());
        dianzan.insert();
        Resp resp = new Resp();
        Chat chat = new Chat();
        chat.setCfrom(dianzan.getUid());
        chat.setCtime(LocalDateTime.now());
        chat.setCtype("d");
        Shunjian shunjian = shunjianService.getById(dianzan.getSid());
        chat.setCto(shunjian.getSuid());
        if (shunjian.getStype().equals("dongtai") || shunjian.getStype().equals("ziyuan") || shunjian.getStype().equals("wenzhang") || shunjian.getStype().equals("answer")) {
            Dianzantext dianzantext = new Dianzantext();
            dianzantext.setUserinfo(this.userinfoService.getById(dianzan.getUid()));
            dianzantext.setShunjian(shunjian);
            chat.setCmessage(JSONArray.toJSONString(dianzantext));
        }
        resp.setData(chat);
        resp.setMessage("Dianzan");
        webSocket.sendMessage(chat.getCto(), resp);
    }

    @PostMapping("/setpinglun")
    public void setpinglun(Pinglun pinglun) throws IOException, EncodeException {
        pinglun.setPtime(LocalDateTime.now());
        pinglun.insert();
        Resp resp = new Resp();
        Chat chat = new Chat();
        chat.setCfrom(pinglun.getUid());
        chat.setCtime(LocalDateTime.now());
        chat.setCtype("p");
        Shunjian shunjian = shunjianService.getById(pinglun.getSid());
        chat.setCto(shunjian.getSuid());
        if (shunjian.getStype().equals("dongtai") || shunjian.getStype().equals("ziyuan") || shunjian.getStype().equals("wenzhang") || shunjian.getStype().equals("answer")) {
            Pingluntext pingluntext = new Pingluntext();
            pingluntext.setUserinfo(this.userinfoService.getById(pinglun.getUid()));
            pingluntext.setShunjian(shunjian);
            pingluntext.setContent(pinglun.getContent());
            pingluntext.setType(pinglun.getPtype());
            if (pinglun.getPtype().equals("reply"))
                chat.setCto(pinglun.getRid());
            chat.setCmessage(JSONArray.toJSONString(pingluntext));
        }
        resp.setData(chat);
        resp.setMessage("Pinglun");
        webSocket.sendMessage(chat.getCto(), resp);
    }

    @PostMapping("/removedianzan")
    public void removedianzan(String sid, String uid) {
        QueryWrapper<Dianzan> dianzanQueryWrapper = new QueryWrapper<>();
        dianzanQueryWrapper.eq("sid", sid);
        dianzanQueryWrapper.eq("uid", uid);
        this.dianzanService.remove(dianzanQueryWrapper);
    }

    @PostMapping("/setshunjian")
    public Resp setshunjian(Shunjian shunjian) {
        Resp resp = new Resp();
        shunjian.setStime(LocalDateTime.now());
        if (shunjian.insert()) {
            resp.setCode(10010);
            resp.setMessage("发布成功");
            return resp;
        } else {
            resp.setCode(10011);
            resp.setMessage("发布失败");
            return resp;
        }
    }

    @PostMapping(value = "/setdongtai")
    public Resp setdongtai(HttpServletRequest request) throws IOException {
        Resp resp = new Resp();
        Shunjian shunjian = new Shunjian();
        shunjian.setStype("dongtai");
        shunjian.setSuid(request.getParameter("uid"));
        shunjian.setStitle(request.getParameter("title"));
        shunjian.setStime(LocalDateTime.now());
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> map = mulReq.getFileMap();
            String url = request.getScheme() + "://" + this.url + ":" + port + "/getimage?url=";
            String basePath = "c://shangchuanwenjian/" + request.getParameter("uid") + "/" + LocalDate.now().toString() + "/" + Uuid.getUuid();
            // key为前端的name属性，value为上传的对象（MultipartFile）
            List<String> imagelist = new ArrayList<>();
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                // 自己的保存文件逻辑
                String filePath = basePath + "/" + entry.getKey();
                File desFile = new File(filePath);
                if (!desFile.getParentFile().exists()) {
                    desFile.mkdirs();
                }
                entry.getValue().transferTo(desFile);
                filePath = url + filePath;
                imagelist.add(filePath);
            }

            shunjian.setScontent(JSONArray.toJSONString(imagelist));
            if (shunjian.insert()) {
                resp.setCode(10010);
                resp.setMessage("发布成功");
                return resp;
            } else {
                resp.setCode(10011);
                resp.setMessage("发布失败");
                return resp;
            }
        } else {
            if (shunjian.insert()) {
                resp.setCode(10010);
                resp.setMessage("发布成功");
                return resp;
            } else {
                resp.setCode(10011);
                resp.setMessage("发布失败");
                return resp;
            }
        }
    }

    //    获取瞬间的数据
    @GetMapping("/getshunjian")
    public Resp getshunjian(String uid, String othersId, String sid, String type, String search, String ttype) {
        Resp resp = new Resp();
        List<Dongtai> dongtaiList = new ArrayList<>();
        //当前用户的好友
        List<String> friendstringlist = new ArrayList<>();
        if (type.equals("friend")) {
            QueryWrapper<Friend> friendQueryWrapper = new QueryWrapper<>();
            friendQueryWrapper.eq("mid", uid);
            List<Friend> friendList = this.friendService.list(friendQueryWrapper);
            for (int i = 0; i < friendList.size(); i++) {
                friendstringlist.add(friendList.get(i).getUid());
            }
            friendstringlist.add(uid);
        }
        //好友的动态
        QueryWrapper<Shunjian> shunjianQueryWrapper = new QueryWrapper<>();
//        如果要的不是回答
        if (!type.equals("answer")) {
            if (!type.equals("one") && !type.equals("single"))
                shunjianQueryWrapper.ne("stype", "answer");
        } else {
            shunjianQueryWrapper.eq("stitle", othersId);
            shunjianQueryWrapper.eq("stype", "answer");
        }
        //如果是查询一个人的一条
        if (type.equals("one"))
            shunjianQueryWrapper.eq("sid", sid);
        else
            shunjianQueryWrapper.lt("sid", sid);
        //如果是查询好友
        if (type.equals("friend") && friendstringlist.size() > 0) {
            shunjianQueryWrapper.in("suid", friendstringlist);
            shunjianQueryWrapper.and(Wrapper -> Wrapper.eq("ttype", '1').or().isNull("ttype"));
        } else if (type.equals("friend"))
            shunjianQueryWrapper.eq("suid", "0");
        //如果是查询一个人的所有
        if (type.equals("single"))
            shunjianQueryWrapper.eq("suid", othersId);
        else if (type.equals("dongtai")) {
            shunjianQueryWrapper.eq("stype", "dongtai");
        } else if (type.equals("wenzhang")) {
            shunjianQueryWrapper.eq("stype", "wenzhang");
        } else if (type.equals("tiwen")) {
            shunjianQueryWrapper.eq("stype", "tiwen");
        } else if (type.equals("ziyuan")) {
            shunjianQueryWrapper.eq("stype", "ziyuan");
        } else if (type.equals("search")) {
            shunjianQueryWrapper.and(Wrapper -> Wrapper.like("scontent", search).or().like("stitle", search));
        } else if (type.equals("task")) {
            shunjianQueryWrapper.eq("stype", "task");
            if (ttype != null) {
                if (!ttype.equals("1")) {
                    shunjianQueryWrapper.eq("ttype", ttype);
                    shunjianQueryWrapper.and(Wrapper -> Wrapper.eq("suid", uid).or().eq("takeid", uid));
                } else {
                    shunjianQueryWrapper.eq("suid", uid);
                }
            } else {
                shunjianQueryWrapper.eq("ttype", "1");
            }
        }
        shunjianQueryWrapper.orderByDesc("sid");
        shunjianQueryWrapper.last("limit 10");
        List<Shunjian> shunjianList = this.shunjianService.list(shunjianQueryWrapper);
        //如果条数不足十条
        if (shunjianList.size() < 10) resp.setMessage("没有更多了");
        for (int i = 0; i < shunjianList.size(); i++) {
            Dongtai dongtai = new Dongtai();
            dongtai.setShunjian(shunjianList.get(i));
            //如果是提问，返回answer的数目
            if (shunjianList.get(i).getStype().equals("tiwen")) {
                dongtai.setAnswernum(this.getAnswerNum(shunjianList.get(i).getSid()));
            }
            //用户信息
            dongtai.setUserinfo(this.userinfoService.getById(shunjianList.get(i).getSuid()));
            //点赞
            List<Map<String, Object>> dianzanList = this.dianzanService.getdianzan(shunjianList.get(i).getSid());
            List<Like> likeList = new ArrayList<>();
            for (int j = 0; j < dianzanList.size(); j++) {
                Map<String, Object> d = dianzanList.get(j);
                Like l = new Like();
                l.setUid(d.get("uid").toString());
                l.setUname(d.get("uname").toString());
                if (d.get("uid").toString().equals(uid))
                    dongtai.setIslike(true);
                likeList.add(l);
            }
            if (dongtai.getIslike() == null) dongtai.setIslike(false);
            dongtai.setLikeList(likeList);
            //评论
            Comments comments = new Comments();
            List<Map<String, Object>> pinglunList = this.pinglunService.getpinglun(shunjianList.get(i).getSid());
            List<Comment> commentList = new ArrayList<>();
            for (int k = 0; k < pinglunList.size(); k++) {
                Map<String, Object> c = pinglunList.get(k);
                Comment comment = new Comment();
                comment.setUid(c.get("uid").toString());
                comment.setUname(c.get("uname").toString());
                comment.setContent(c.get("content").toString());
                comment.setPtype(c.get("ptype").toString());
                commentList.add(comment);
            }
            comments.setTotal(pinglunList.size());
            comments.setCommentList(commentList);
            dongtai.setComments(comments);
            dongtaiList.add(dongtai);
        }
        resp.setData(dongtaiList);
        return resp;
    }

    public int getAnswerNum(BigInteger sid) {
        QueryWrapper<Shunjian> shunjianQueryWrapper = new QueryWrapper<>();
        shunjianQueryWrapper.eq("stype", "answer");
        shunjianQueryWrapper.eq("stitle", sid);
        return this.shunjianService.count(shunjianQueryWrapper);
    }

    @GetMapping("/getshunjianone")
    public Shunjian getshunjianone(BigInteger sid) {
        return this.shunjianService.getById(sid);
    }

    //    获取首页的数据
    @GetMapping("/getIndexData")
    public List<DongtaiByType> getIndexData(String uid) {
        List<DongtaiByType> dongtaiByTypes = new ArrayList<>();
//        动态
        Resp dongtai = this.getshunjian(uid, "", "999999999999", "dongtai", "", "");
        DongtaiByType d = new DongtaiByType();
        d.setInfolist((List<Dongtai>) dongtai.getData());
        if (dongtai.getMessage() == null)
            d.setLoadtext("上拉加载更多");
        else {
            d.setLoadtext("没有更多了");
        }
        dongtaiByTypes.add(d);
//        图文
        Resp wenzhang = this.getshunjian(uid, "", "999999999999", "wenzhang", "", "");
        DongtaiByType w = new DongtaiByType();
        w.setInfolist((List<Dongtai>) wenzhang.getData());
        w.setLoadtext(wenzhang.getMessage().equals("没有更多了") ? "没有更多了" : "上拉加载更多");
        dongtaiByTypes.add(w);
//        问题
        Resp tiwen = this.getshunjian(uid, "", "999999999999", "tiwen", "", "");
        DongtaiByType t = new DongtaiByType();
        t.setInfolist((List<Dongtai>) tiwen.getData());
        t.setLoadtext(tiwen.getMessage().equals("没有更多了") ? "没有更多了" : "上拉加载更多");
        dongtaiByTypes.add(t);
//        资源
        Resp ziyuan = this.getshunjian(uid, "", "999999999999", "ziyuan", "", "");
        DongtaiByType z = new DongtaiByType();
        z.setInfolist((List<Dongtai>) ziyuan.getData());
        z.setLoadtext(ziyuan.getMessage().equals("没有更多了") ? "没有更多了" : "上拉加载更多");
        dongtaiByTypes.add(z);
//                资源
        Resp task = this.getshunjian(uid, "", "999999999999", "task", "", null);
        DongtaiByType j = new DongtaiByType();
        j.setInfolist((List<Dongtai>) task.getData());
        j.setLoadtext(task.getMessage().equals("没有更多了") ? "没有更多了" : "上拉加载更多");
        dongtaiByTypes.add(j);
        return dongtaiByTypes;
    }

    @PostMapping("/payjifen")
    public Resp payjifen(BigInteger sid, String uid, int num, String suid) {
        Resp resp = new Resp();
        QueryWrapper<Goumai> goumaiQueryWrapper = new QueryWrapper<>();
        goumaiQueryWrapper.eq("guid", uid);
        goumaiQueryWrapper.eq("gsid", sid);
        if (this.goumaiService.getOne(goumaiQueryWrapper) != null) {
            resp.setCode(10018);
            resp.setMessage("已购买");
            return resp;
        } else {
            Jifen jifen = this.jifenService.getById(uid);
            if (jifen.getJnum() - num < 0) {
                resp.setCode(10019);
                resp.setMessage("积分不足");
                return resp;
            } else {
                Jifen sjifen = this.jifenService.getById(suid);
                sjifen.setJnum(sjifen.getJnum() + num / 2);
                sjifen.updateById();
                jifen.setJnum(jifen.getJnum() - num);
                jifen.updateById();
                Goumai goumai = new Goumai();
                goumai.setGsid(sid);
                goumai.setGuid(uid);
                goumai.insert();
                resp.setCode(10018);
                resp.setMessage("已购买");
                resp.setData(jifen.getJnum());
                return resp;
            }
        }
    }

    @PostMapping("/addTask")
    public Resp addTask(String uid, String title, String content, int sjifen, int fjifen, int xinyufen, @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date endtime) {
        Resp resp = new Resp();
        //如果积分不足
        Jifen jifen = this.jifenService.getById(uid);
        if (jifen.getJnum() - sjifen < 0) {
            resp.setMessage("积分不足");
            return resp;
        }
        //
        Shunjian shunjian = new Shunjian();
        shunjian.setStype("task");
        shunjian.setScontent(content);
        shunjian.setStime(LocalDateTime.now());
        shunjian.setSuid(uid);
        shunjian.setStitle(title);
        shunjian.setEndtime(endtime);
        shunjian.setFjifen(fjifen);
        shunjian.setSjifen(sjifen);
        shunjian.setXinyufen(xinyufen);
        shunjian.setTtype("1");
        jifen.setJnum(jifen.getJnum() - sjifen);
        if (shunjian.insert() && jifen.updateById()) {
            resp.setCode(10021);
            resp.setMessage("任务发布成功");
        } else {
            resp.setMessage("任务发布失败");
        }
        return resp;
    }

    @PostMapping("/takeTask")
    public Resp takeTask(String uid, String sid) throws IOException, EncodeException {
        Resp resp = new Resp();
        Jifen jifen = this.jifenService.getById(uid);
        Shunjian shunjian = this.shunjianService.getById(sid);
        if (jifen.getJnum() - shunjian.getFjifen() < 0) {
            resp.setMessage("您的积分值扣除失败的积分不足0，无法接受任务");
            return resp;
        }
        if (jifen.getXinyufen() - shunjian.getXinyufen() < 0) {
            resp.setMessage("您的信誉分不达标，无法接受任务");
            return resp;
        }

        if (shunjian == null) {
            resp.setMessage("该任务已被删除");
            return resp;
        }
        if (shunjian.getSuid().equals(uid)) {
            resp.setMessage("不可以接自己的任务");
            return resp;
        }
        if (!shunjian.getTtype().equals("1")) {
            resp.setMessage("该任务已被他人接受或超时");
            return resp;
        } else {
            shunjian.setTtype("2");
            shunjian.setTakeid(uid);
            shunjian.updateById();
            jifen.setJnum(jifen.getJnum() - shunjian.getFjifen());
            jifen.updateById();
            resp.setCode(10022);
            resp.setMessage("任务接受成功");
            Resp resp1 = new Resp();
            Chat chat = new Chat();
            chat.setCfrom(uid);
            chat.setCtime(LocalDateTime.now());
            chat.setCtype("j");
            chat.setCto(shunjian.getSuid());
            Dianzantext dianzantext = new Dianzantext();
            dianzantext.setUserinfo(this.userinfoService.getById(uid));
            dianzantext.setShunjian(shunjian);
            chat.setCmessage(JSONArray.toJSONString(dianzantext));

            resp1.setData(chat);
            resp1.setMessage("Task");
            this.webSocket.sendMessage(chat.getCto(), resp1);
            return resp;
        }
    }

    @PostMapping("/pushTask")
    public Resp pushTask(String sid, String pushtext) throws IOException, EncodeException {
        Resp resp = new Resp();
        Shunjian shunjian = this.shunjianService.getById(sid);
        if (!shunjian.getTtype().equals("2")) {
            resp.setMessage("该任务当前不可提交，已完成或者已失败");
            return resp;
        }
        shunjian.setTtype("3");
        shunjian.setPushtext(pushtext);
        if (shunjian.updateById()) {
            Jifen jifen = jifenService.getById(shunjian.getTakeid());
            jifen.setJnum(jifen.getJnum() + shunjian.getFjifen() + shunjian.getSjifen());
            jifen.updateById();
            resp.setCode(10023);
            resp.setMessage("任务成功");
            Resp resp1 = new Resp();
            Chat chat = new Chat();
            chat.setCfrom(shunjian.getTakeid());
            chat.setCtime(LocalDateTime.now());
            chat.setCtype("j");
            chat.setCto(shunjian.getSuid());
            Dianzantext dianzantext = new Dianzantext();
            dianzantext.setUserinfo(this.userinfoService.getById(shunjian.getTakeid()));
            dianzantext.setShunjian(shunjian);
            chat.setCmessage(JSONArray.toJSONString(dianzantext));

            resp1.setData(chat);
            resp1.setMessage("Task");
            this.webSocket.sendMessage(chat.getCto(), resp1);
        } else {
            resp.setMessage("任务提交失败");
        }
        return resp;
    }

    @PostMapping("/removeshunjian")
    public Resp removeshunjian(String sid) {
        Resp resp = new Resp();
        Shunjian shunjian = this.shunjianService.getById(sid);
        if(shunjian.getStype().equals("ziyuan")){
            resp.setMessage("资源不能删除");
            return resp;
        }
        List<Shunjian> shunjianList = new ArrayList<>();
        shunjianList.add(shunjian);
        //如果是提问，要连回答一起删除
        if (shunjian.getStype().equals("tiwen")) {
            QueryWrapper<Shunjian> shunjianQueryWrapper = new QueryWrapper<>();
            shunjianQueryWrapper.eq("stype", "answer");
            shunjianQueryWrapper.eq("stitle", shunjian.getSid());
            List<Shunjian> list = this.shunjianService.list(shunjianQueryWrapper);
            for (int i = 0; i < list.size(); i++) shunjianList.add(list.get(i));
        } else if (shunjian.getStype().equals("task")) {//如果是任务，且正在进行，要将积分加好
            if(shunjian.getTtype().equals("1")){
                Jifen jifen = this.jifenService.getById(shunjian.getSuid());
                jifen.setJnum(jifen.getJnum()+shunjian.getSjifen());
                jifen.updateById();
            }else if(shunjian.getTtype().equals("2")){
                Jifen jifen = this.jifenService.getById(shunjian.getTakeid());
                jifen.setJnum(jifen.getJnum()+shunjian.getSjifen()+shunjian.getFjifen());
                jifen.updateById();
            }
        }
        for(int j=0;j<shunjianList.size();j++){
            QueryWrapper<Dianzan>dianzanQueryWrapper=new QueryWrapper<>();
            dianzanQueryWrapper.eq("sid",shunjianList.get(j).getSid());
            this.dianzanService.remove(dianzanQueryWrapper);
            QueryWrapper<Pinglun>pinglunQueryWrapper=new QueryWrapper<>();
            pinglunQueryWrapper.eq("sid",shunjianList.get(j).getSid());
            this.pinglunService.remove(pinglunQueryWrapper);
            shunjianList.get(j).deleteById();
            resp.setCode(10012);
            resp.setMessage("删除瞬间成功");
        }
        return resp;
    }
}

class DongtaiByType {
    String loadtext;
    List<Dongtai> infolist;

    public String getLoadtext() {
        return loadtext;
    }

    public void setLoadtext(String loadtext) {
        this.loadtext = loadtext;
    }

    public List<Dongtai> getInfolist() {
        return infolist;
    }

    public void setInfolist(List<Dongtai> infolist) {
        this.infolist = infolist;
    }
}

class Dongtai {
    Userinfo userinfo;
    Shunjian shunjian;
    Boolean islike;
    List<Like> likeList;
    Comments comments;
    int answernum;

    public int getAnswernum() {
        return answernum;
    }

    public void setAnswernum(int answernum) {
        this.answernum = answernum;
    }

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public Shunjian getShunjian() {
        return shunjian;
    }

    public void setShunjian(Shunjian shunjian) {
        this.shunjian = shunjian;
    }

    public Boolean getIslike() {
        return islike;
    }

    public void setIslike(Boolean islike) {
        this.islike = islike;
    }

    public List<Like> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<Like> likeList) {
        this.likeList = likeList;
    }
}

class Like {
    String uid;
    String uname;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}

class Comments {
    int total;
    List<Comment> commentList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}

class Comment {
    String uid;
    String uname;
    String content;
    String ptype;

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

class Pingluntext {
    Userinfo userinfo;
    Shunjian shunjian;
    String content;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}