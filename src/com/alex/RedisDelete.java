package com.alex;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;

/**
 * @author liangxiaofei
 * @date 2021/4/20 17:00
 */
public class RedisDelete {
    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create("redis://192.168.18.61:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        String keyPattern = "2004*";
        List<String> keyList = syncCommands.keys(keyPattern);
        for (String key : keyList) {
            System.out.println("delete key: " + key);
            Long del = syncCommands.del(key);
            System.out.println("delete key: " + key + ", result: " + del);
        }

        connection.close();
        redisClient.shutdown();
    }
}
