package com.woyaofenxiang.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-12
 */
public class Dianzan extends Model<Dianzan> {

    private static final long serialVersionUID = 1L;

    /**
     * sid
     */
    private BigInteger sid;

    /**
     * uid
     */
    private String uid;

    private LocalDateTime dtime;


    public BigInteger getSid() {
        return sid;
    }

    public void setSid(BigInteger sid) {
        this.sid = sid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDateTime getDtime() {
        return dtime;
    }

    public void setDtime(LocalDateTime dtime) {
        this.dtime = dtime;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "Dianzan{" +
        "sid=" + sid +
        ", uid=" + uid +
        ", dtime=" + dtime +
        "}";
    }
}
