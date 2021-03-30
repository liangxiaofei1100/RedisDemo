package com.alex;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;

/**
 * @author liangxiaofei
 * @date 2021/3/30 10:46
 */
public class RedisKeyRename {
    public static void main(String[] args) {
        // write your code here
        RedisClient redisClient = RedisClient.create("redis://172.31.249.145:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        String keyPattern = "epoocloud-data:building:*";
        List<String> keyList = syncCommands.keys(keyPattern);
        for (String key : keyList) {
            String value = syncCommands.get(key);

            // 重命名key
            String newKey = getNewKey(key);
            syncCommands.set(newKey, value);
        }

        connection.close();
        redisClient.shutdown();
    }

    public static String getNewKey(String key) {
        int index = "epoocloud-data:building:".length();
        return key.substring(0, index) + "1001:" + key.substring(index);
    }
}
