package com.woyaofenxiang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-19
 */
public class Fankui extends Model<Fankui> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Integer fid;

    private String fcontent;


    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFcontent() {
        return fcontent;
    }

    public void setFcontent(String fcontent) {
        this.fcontent = fcontent;
    }

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

    @Override
    public String toString() {
        return "Fankui{" +
        "fid=" + fid +
        ", fcontent=" + fcontent +
        "}";
    }
}
