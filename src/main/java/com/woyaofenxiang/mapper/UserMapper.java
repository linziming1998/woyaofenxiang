package com.woyaofenxiang.mapper;

import com.woyaofenxiang.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woyaofenxiang.entity.Userinfo;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 林梓铭
 * @since 2020-03-24
 */
public interface UserMapper extends BaseMapper<User> {
    public Userinfo getUserInfo(@Param("sid")String num);
}
