package com.woyaofenxiang.service.impl;

import com.woyaofenxiang.entity.Pinglun;
import com.woyaofenxiang.mapper.PinglunMapper;
import com.woyaofenxiang.service.PinglunService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-12
 */
@Service
public class PinglunServiceImpl extends ServiceImpl<PinglunMapper, Pinglun> implements PinglunService {

    @Override
    public List<Map<String, Object>> getpinglun(BigInteger sid) {
        return this.baseMapper.getpinglun(sid);
    }
}
