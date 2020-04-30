package com.woyaofenxiang.service;

import com.woyaofenxiang.entity.Dianzan;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

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
public interface DianzanService extends IService<Dianzan> {
    public List<Map<String,Object>> getdianzan(BigInteger sid);
}
