package com.woyaofenxiang.service;

import com.woyaofenxiang.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woyaofenxiang.entity.Userinfo;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-24
 */
public interface UserService extends IService<User> {
    public Userinfo getUserInfo(String num);
}
