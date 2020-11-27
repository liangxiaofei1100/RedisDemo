package com.alex;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class Main {

    public static void main(String[] args) {
        // write your code here
        RedisClient redisClient = RedisClient.create("redis://192.168.0.139:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        String dataHeatSource = getDataHeatSource();
        String key = "epoocloud-data:heat_source:yc_T001:realtime";
        syncCommands.set(key, dataHeatSource);

        String value = syncCommands.get(key);
        System.out.println(value);

        connection.close();
        redisClient.shutdown();
    }

    public static String getDataHeatSource() {
        return "[\n" +
                "  \"cn.com.epoo.epoocloud.data.domain.entity.data.DataHeatSource\",\n" +
                "  {\n" +
                "    \"id\": 2259061,\n" +
                "    \"crtTime\": [\n" +
                "      \"java.util.Date\",\n" +
                "      \"2020-10-27 15:29:00\"\n" +
                "    ],\n" +
                "    \"entCode\": \"yc\",\n" +
                "    \"heatCode\": \"T001\",\n" +
                "    \"dataTime\": [\n" +
                "      \"java.util.Date\",\n" +
                "      \"2020-10-27 15:29:00\"\n" +
                "    ],\n" +
                "    \"supply_t\": \"62.3\",\n" +
                "    \"back_t\": \"33.6\",\n" +
                "    \"supply_p\": \"0.41\",\n" +
                "    \"back_p\": \"0.38\",\n" +
                "    \"supply_f\": \"1008\",\n" +
                "    \"supply_h\": \"118\",\n" +
                "    \"supply_f_total\": \"13615300\",\n" +
                "    \"supply_h_total\": \"1679840\",\n" +
                "    \"belt_total\": \"3556\",\n" +
                "    \"belt_f\": \"0\",\n" +
                "    \"belt_speed\": \"0\"\n" +
                "  }\n" +
                "]";
    }
}
