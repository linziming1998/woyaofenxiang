package com.woyaofenxiang.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.ToString;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-24
 */
public class Userinfo extends Model<Userinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId
    private String uid;

    /**
     * 用户昵称
     */
    private String uname;

    /**
     * 用户性别
     */
    private String usex;

    /**
     * 用户的头像
     */
    private String uimage;

    /**
     * 用户的生日
     */
    private LocalDate ubirthday;

    /**
     * 用户的注册时间
     */
    private LocalDate uregtime;

    /**
     * 是否为新用户
     */
    private String uisnew;
    /**
     * 地址
     */
    private String uaddress;
    /**
     * 分享id
     */
    private String usharenum;


    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

    public String getUsharenum() {
        return usharenum;
    }

    public void setUsharenum(String usharenum) {
        this.usharenum = usharenum;
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

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getUimage() {
        return uimage;
    }

    public void setUimage(String uimage) {
        this.uimage = uimage;
    }

    public LocalDate getUbirthday() {
        return ubirthday;
    }

    public void setUbirthday(LocalDate ubirthday) {
        this.ubirthday = ubirthday;
    }

    public LocalDate getUregtime() {
        return uregtime;
    }

    public void setUregtime(LocalDate uregtime) {
        this.uregtime = uregtime;
    }

    public String getUisnew() {
        return uisnew;
    }

    public void setUisnew(String uisnew) {
        this.uisnew = uisnew;
    }

    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

    @Override
    public String toString() {
        return "Userinfo{" +
        "uid=" + uid +
        ", uname=" + uname +
        ", usex=" + usex +
        ", uimage=" + uimage +
        ", ubirthday=" + ubirthday +
        ", uregtime=" + uregtime +
        ", uisnew=" + uisnew +
        "}";
    }
}
