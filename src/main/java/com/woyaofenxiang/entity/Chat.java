package com.woyaofenxiang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-29
 */
public class Chat extends Model<Chat> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    /**
     * 发送者的id
     */
    private String cfrom;

    /**
     * 接受者的id
     */
    private String cto;

    /**
     * 消息
     */
    private String cmessage;

    /**
     * 发送的时间
     */
    private LocalDateTime ctime;

    /**
     * 类型
     */
    private String ctype;


    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCfrom() {
        return cfrom;
    }

    public void setCfrom(String cfrom) {
        this.cfrom = cfrom;
    }

    public String getCto() {
        return cto;
    }

    public void setCto(String cto) {
        this.cto = cto;
    }

    public String getCmessage() {
        return cmessage;
    }

    public void setCmessage(String cmessage) {
        this.cmessage = cmessage;
    }

    public LocalDateTime getCtime() {
        return ctime;
    }

    public void setCtime(LocalDateTime ctime) {
        this.ctime = ctime;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    @Override
    protected Serializable pkVal() {
        return this.cid;
    }

    @Override
    public String toString() {
        return "Chat{" +
        "cid=" + cid +
        ", cfrom=" + cfrom +
        ", cto=" + cto +
        ", cmessage=" + cmessage +
        ", ctime=" + ctime +
        ", ctype=" + ctype +
        "}";
    }
}
