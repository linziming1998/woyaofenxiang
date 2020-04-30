package com.woyaofenxiang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-18
 */
public class Goumai extends Model<Goumai> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private BigInteger id;

    private BigInteger gsid;

    private String guid;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getGsid() {
        return gsid;
    }

    public void setGsid(BigInteger gsid) {
        this.gsid = gsid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    protected Serializable pkVal() {
        return this.gsid;
    }

    @Override
    public String toString() {
        return "Goumai{" +
        "gsid=" + gsid +
        ", guid=" + guid +
        "}";
    }
}
