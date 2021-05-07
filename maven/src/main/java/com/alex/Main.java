package com.alex;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * @author liangxiaofei
 * @date 2021/5/7 15:44
 */
public class Main {
    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create("redis://192.168.18.61:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        String value = syncCommands.get("roomData:1001:200400006");
        System.out.println(value);

        connection.close();
        redisClient.shutdown();

    }
}
