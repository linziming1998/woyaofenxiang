package com.woyaofenxiang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-24
 */
public class Shunjian extends Model<Shunjian> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "sid", type = IdType.AUTO)
    private BigInteger sid;

    /**
     * 用户id
     */
    private String suid;

    /**
     * 内容
     */
    private String scontent;

    /**
     * 发布时间
     */
    private LocalDateTime stime;

    /**
     * 类型
     */
    private String stype;

    /**
     * 标题
     */
    private String stitle;

    private int sjifen;

    private int fjifen,xinyufen;
    private Date endtime;

    private String ttype;
    private String takeid;
    private String pushtext;

    public String getPushtext() {
        return pushtext;
    }

    public void setPushtext(String pushtext) {
        this.pushtext = pushtext;
    }

    public String getTakeid() {
        return takeid;
    }

    public void setTakeid(String takeid) {
        this.takeid = takeid;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public int getSjifen() {
        return sjifen;
    }

    public void setSjifen(int sjifen) {
        this.sjifen = sjifen;
    }

    public int getFjifen() {
        return fjifen;
    }

    public void setFjifen(int fjifen) {
        this.fjifen = fjifen;
    }

    public int getXinyufen() {
        return xinyufen;
    }

    public void setXinyufen(int xinyufen) {
        this.xinyufen = xinyufen;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public BigInteger getSid() {
        return sid;
    }

    public void setSid(BigInteger sid) {
        this.sid = sid;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public String getScontent() {
        return scontent;
    }

    public void setScontent(String scontent) {
        this.scontent = scontent;
    }

    public LocalDateTime getStime() {
        return stime;
    }

    public void setStime(LocalDateTime stime) {
        this.stime = stime;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    @Override
    protected Serializable pkVal() {
        return this.sid;
    }

    @Override
    public String toString() {
        return "Shunjian{" +
        "sid=" + sid +
        ", suid=" + suid +
        ", scontent=" + scontent +
        ", stime=" + stime +
        ", stype=" + stype +
        ", stitle=" + stitle +
        "}";
    }
}
