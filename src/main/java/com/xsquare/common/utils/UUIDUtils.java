package com.xsquare.common.utils;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author yusongyuan
 * @create 2018-02-24 15:57
 **/

public class UUIDUtils {

    /**
     * @Author: YuSongYuan
     * @Description: 获取UUID， 32位
     *
     * @Date: 2018/1/18 19:07
     **/
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
