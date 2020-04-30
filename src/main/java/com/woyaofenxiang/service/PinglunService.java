package com.woyaofenxiang.service;

import com.woyaofenxiang.entity.Pinglun;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-12
 */
public interface PinglunService extends IService<Pinglun> {
    public List<Map<String,Object>> getpinglun(BigInteger sid);
}
