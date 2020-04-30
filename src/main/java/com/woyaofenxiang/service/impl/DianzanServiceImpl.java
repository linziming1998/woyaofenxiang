package com.woyaofenxiang.service.impl;

import com.woyaofenxiang.entity.Dianzan;
import com.woyaofenxiang.mapper.DianzanMapper;
import com.woyaofenxiang.service.DianzanService;
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
public class DianzanServiceImpl extends ServiceImpl<DianzanMapper, Dianzan> implements DianzanService {

    @Override
    public List<Map<String, Object>> getdianzan(BigInteger sid) {
        return this.baseMapper.getdianzan(sid);
    }
}
