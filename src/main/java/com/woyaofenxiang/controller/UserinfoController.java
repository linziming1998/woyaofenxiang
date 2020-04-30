package com.woyaofenxiang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woyaofenxiang.entity.Shunjian;
import com.woyaofenxiang.entity.Userinfo;
import com.woyaofenxiang.service.UserinfoService;
import com.woyaofenxiang.util.Resp;
import com.woyaofenxiang.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-24
 */
@RestController
@RequestMapping("/userinfo")
public class UserinfoController {
    @Value("${data.url}")
    String url;
    @Value("${server.port}")
    String port;
    @Autowired
    UserinfoService userinfoService;
    @PostMapping("/getUserinfo")
    public Userinfo getUserinfo(String uid){
        return this.userinfoService.getById(uid);
    }
    @PostMapping("/reginfo")
    public Resp reginfo(Userinfo userinfo){
        Resp resp=new Resp();
        userinfo.setUisnew("0");
        if(userinfo.updateById()){
            resp.setCode(10008);
            resp.setMessage("用户信息更改成功");
            return resp;
        }else {
            resp.setCode(10009);
            resp.setMessage("用户信息更改失败");
            return resp;
        }
    }
    @PostMapping("/checkCanUse")
    public Resp checkCanUse(String name) {
        Resp resp=new Resp();
        QueryWrapper<Userinfo>userinfoQueryWrapper=new QueryWrapper<>();
        userinfoQueryWrapper.eq("usharenum",name);
        if(this.userinfoService.getOne(userinfoQueryWrapper)==null){
            resp.setMessage("该分享快乐码可以使用");
        }else{
            resp.setMessage("该分享快乐码已被注册");
        }
        return resp;
    }
    @PostMapping("/update")
    public Resp update(HttpServletRequest request, @RequestParam("file")MultipartFile [] files)throws IOException {
        Resp resp=new Resp();
        String uid=request.getParameter("uid");
        String uname=request.getParameter("uname");
        String usex=request.getParameter("usex");
        String ubirthday=request.getParameter("ubirthday");
        String uaddress=request.getParameter("uaddress");
        String usharenum=request.getParameter("usharenum");
        Userinfo userinfo=this.userinfoService.getById(uid);
        if(userinfo.getUsharenum()==null){
            if(this.checkCanUse(usharenum).getMessage().equals("该分享快乐码已被注册"))
            {
                resp.setCode(10009);
                resp.setMessage("该分享快乐码已被注册");
                return  resp;
            }
            userinfo.setUsharenum(usharenum);
        }
        userinfo.setUisnew("0");
        userinfo.setUaddress(uaddress);
        userinfo.setUname(uname);
        userinfo.setUsex(usex);
        userinfo.setUbirthday(LocalDate.parse(ubirthday));
        String url=request.getScheme()+"://"+this.url+":"+port+"/getimage?url=";
        String basePath="c://shangchuanwenjian/"+request.getParameter("uid")+"/uimage"+LocalDate.now().toString();
        File desFile = new File(basePath);
        String uimage=url+basePath;
        if (!desFile.getParentFile().exists()) {
            desFile.mkdirs();
        }
        try {
            files[0].transferTo(desFile);
            userinfo.setUimage(uimage);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        if(userinfo.updateById()){
            resp.setCode(10008);
            resp.setMessage("用户信息更改成功");
            return resp;
        }
        else {
            resp.setCode(10009);
            resp.setMessage("用户信息更改失败");
            return resp;
        }
    }
}
