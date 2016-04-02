package com.altyazi;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class MapUtil {

    /**
     * @param map1<String,String>
     * @param map2<String,String>
     * @return map<String,String>
     */
    public static Map<String, String> mergeMyTwoMaps(Map<String, String> map1, Map<String, String> map2) {
        return ImmutableMap.<String, String>builder()
                .putAll(map1)
                .putAll(map2)
                .build();
    }
}
