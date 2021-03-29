package com.alex;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liangxiaofei
 * @date 2021/3/29 14:59
 */
public class RedisCopy {
    public static void main(String[] args) {
        // 192.168.0.139
        String sourceRedisUrl = "redis://172.31.249.145:6379/0";
        String distRedisUrl = "redis://2dxZTH02tqQqAGEMUFBq@r-m5ejp5azuulvev4v84.redis.rds.aliyuncs.com:6379/0";
        String keyPattern = "epoocloud-data:building:*";

        Map<String, String> data = readData(sourceRedisUrl, keyPattern);
        System.out.println("Read data finished. total data: " + data.size());
        System.out.println(data);

        writeData(distRedisUrl, data);
        System.out.println("Write data finished.");
    }

    private static Map<String, String> readData(String redisUrl, String keyPattern) {
        System.out.println("Start read data from " + redisUrl + ". Key pattern: " + keyPattern);
        RedisClient sourceRedisClient = RedisClient.create(redisUrl);
        StatefulRedisConnection<String, String> sourceRedisConnection = sourceRedisClient.connect();
        RedisCommands<String, String> sourceRedisCommands = sourceRedisConnection.sync();

        List<String> keyList = sourceRedisCommands.keys(keyPattern);

        Map<String, String> data = new HashMap<>();
        for (String key : keyList) {
            String value = sourceRedisCommands.get(key);
            data.put(key, value);
        }

        // 关闭连接
        sourceRedisConnection.close();
        sourceRedisClient.shutdown();
        return data;
    }

    private static void writeData(String redisUrl, Map<String, String> data) {
        System.out.println("Start write data into " + redisUrl);
        RedisClient redisClient = RedisClient.create(redisUrl);
        StatefulRedisConnection<String, String> redisConnection = redisClient.connect();
        RedisCommands<String, String> redisCommands = redisConnection.sync();

        Set<String> stringSet = data.keySet();
        for (String key : stringSet) {
            redisCommands.set(key, data.get(key));
        }

        // 关闭连接
        redisConnection.close();
        redisClient.shutdown();
    }
}
