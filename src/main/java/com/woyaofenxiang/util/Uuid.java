package com.woyaofenxiang.util;

import java.util.UUID;

/**
 * @author linziming
 * @create 2020-02-19 16:04
 */
public class Uuid {
    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
