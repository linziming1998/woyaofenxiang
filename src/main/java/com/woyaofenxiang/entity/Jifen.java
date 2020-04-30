package com.woyaofenxiang.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-18
 */
public class Jifen extends Model<Jifen> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的id
     */
    @TableId
    private String juid;

    /**
     * 积分
     */
    private Integer jnum;
    /**
     * 信誉分
     */
    private Integer xinyufen;

    /**
     * 时间
     */
    private LocalDate jtime;


    public Integer getXinyufen() {
        return xinyufen;
    }

    public void setXinyufen(Integer xinyufen) {
        this.xinyufen = xinyufen;
    }

    public String getJuid() {
        return juid;
    }

    public void setJuid(String juid) {
        this.juid = juid;
    }

    public Integer getJnum() {
        return jnum;
    }

    public void setJnum(Integer jnum) {
        this.jnum = jnum;
    }

    public LocalDate getJtime() {
        return jtime;
    }

    public void setJtime(LocalDate jtime) {
        this.jtime = jtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.juid;
    }

    @Override
    public String toString() {
        return "Jifen{" +
        "juid=" + juid +
        ", jnum=" + jnum +
        ", jtime=" + jtime +
        "}";
    }
}
