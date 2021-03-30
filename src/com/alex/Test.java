package com.alex;

/**
 * @author liangxiaofei
 * @date 2021/3/30 11:01
 */
public class Test {
    public static void main(String[] args) {
        String s = "epoocloud-data:building:1_1";
        String newKey = RedisKeyRename.getNewKey(s);
        System.out.println(newKey);
    }
}
