package com.woyaofenxiang.service;

import com.woyaofenxiang.entity.Friend;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-05
 */
public interface FriendService extends IService<Friend> {
    public List<Map<String,Object>> getFriendList(String uid);
    public List<Map<String,Object>> getFriendInfo(String uid,String mid);
}
