package com.woyaofenxiang.service.impl;

import com.woyaofenxiang.entity.Friend;
import com.woyaofenxiang.mapper.FriendMapper;
import com.woyaofenxiang.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-05
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Override
    public List<Map<String, Object>> getFriendList(String uid) {
        return this.baseMapper.getFriendList(uid);
    }

    @Override
    public List<Map<String, Object>> getFriendInfo(String uid, String mid) {
        return this.baseMapper.getFriendInfo(uid,mid);
    }
}
