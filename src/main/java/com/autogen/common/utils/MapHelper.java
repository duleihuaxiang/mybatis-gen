package com.autogen.common.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class MapHelper {
    public static class MapBuilder<K, V> {
        private Map<K, V> map = new HashMap<K, V>();

        public MapBuilder<K, V> put(K key, V value) {
            this.map.put(key, value);
            return this;
        }

        public Map<K, V> getMap() {
            return this.map;
        }
    }

    public static <K, V> MapBuilder<K, V> put(K key, V value) {
        MapBuilder<K, V> mapBuilder = new MapBuilder<K, V>();
        mapBuilder.put(key, value);
        return mapBuilder;
    }
}
