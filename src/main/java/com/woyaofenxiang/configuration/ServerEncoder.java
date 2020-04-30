package com.woyaofenxiang.configuration;

import com.alibaba.fastjson.JSONObject;
import com.woyaofenxiang.entity.Chat;
import com.woyaofenxiang.util.Resp;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author linziming
 * @create 2020-03-28 18:54
 */
public class ServerEncoder implements Encoder.Text<Resp> {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(EndpointConfig arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public String encode(Resp resp) throws EncodeException {
        return JSONObject.toJSONString(resp);
    }
}