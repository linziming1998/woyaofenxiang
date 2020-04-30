package com.woyaofenxiang.service.impl;

import com.woyaofenxiang.entity.User;
import com.woyaofenxiang.entity.Userinfo;
import com.woyaofenxiang.mapper.UserMapper;
import com.woyaofenxiang.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Userinfo getUserInfo(String num) {
        return this.baseMapper.getUserInfo(num);
    }
}
