package com.autogen.common.base.result;

import java.util.Map;

/**
 * Created by fuqinqin on 2017/7/31.
 */
public class MapResult {

    private Map<String, Object> result;

    public MapResult(Map<String, Object> map){
        this.result = map;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MapResult{" +
                "result=" + result +
                '}';
    }
}
