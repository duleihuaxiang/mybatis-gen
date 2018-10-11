package com.autogen.common.base.controller;


import com.autogen.common.base.result.ResultCode;

import com.autogen.common.utils.MapHelper;


import java.util.Map;

public class BaseController {

    /**
     * 获取返回map
     */
    protected Map<String, Object> resultMap(ResultCode resultCode) {
        return resultMap(resultCode, null);
    }

    protected Map<String, Object> resultMap(ResultCode resultCode, Object... objects) {
        Map<String, Object> resultMap = new MapHelper.MapBuilder<String, Object>().getMap();
        resultMap.put("code", resultCode.getCode());
        resultMap.put("message", resultCode.getMessage());

        if (objects != null) {
            if (objects.length % 2 == 0) {
                for (int i = 0; i < objects.length; i += 2) {
                    Object key = objects[i];
                    Object value = objects[i + 1];

                    if (!(key instanceof String)) {
                        continue;
                    }

                    resultMap.put((String) key, value);
                }
            }
        }

        return resultMap;
    }
}
