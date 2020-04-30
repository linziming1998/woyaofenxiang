package com.woyaofenxiang.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-05
 */
public class Friend extends Model<Friend> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String fid;

    /**
     * 我的id
     */
    private String mid;

    /**
     * 朋友的id
     */
    private String uid;

    /**
     * 添加时间
     */
    private LocalDateTime ftime;


    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDateTime getFtime() {
        return ftime;
    }

    public void setFtime(LocalDateTime ftime) {
        this.ftime = ftime;
    }

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

    @Override
    public String toString() {
        return "Friend{" +
        "fid=" + fid +
        ", mid=" + mid +
        ", uid=" + uid +
        ", ftime=" + ftime +
        "}";
    }
}
