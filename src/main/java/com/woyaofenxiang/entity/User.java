package com.woyaofenxiang.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-24
 */
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;
    @TableId
    private String uid;

    /**
     * 手机号
     */
    private String uphone;

    /**
     * 密码
     */
    private String upassword;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

    @Override
    public String toString() {
        return "User{" +
        "uid=" + uid +
        ", uphone=" + uphone +
        ", upassword=" + upassword +
        "}";
    }
}
