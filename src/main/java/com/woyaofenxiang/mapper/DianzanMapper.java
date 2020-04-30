package com.woyaofenxiang.mapper;

import com.woyaofenxiang.entity.Dianzan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 林梓铭
 * @since 2020-04-12
 */
public interface DianzanMapper extends BaseMapper<Dianzan> {
    public List<Map<String,Object>> getdianzan(@Param("sid") BigInteger sid);
}
