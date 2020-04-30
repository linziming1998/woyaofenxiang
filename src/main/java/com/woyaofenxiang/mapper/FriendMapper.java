package com.woyaofenxiang.mapper;

import com.woyaofenxiang.entity.Friend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woyaofenxiang.entity.Userinfo;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-05
 */
public interface FriendMapper extends BaseMapper<Friend> {
    public List<Map<String,Object>> getFriendList(@Param("uid")String uid);
    public List<Map<String,Object>> getFriendInfo(@Param("uid")String uid,@Param("mid")String mid);
}
