package com.zzh.gdut.gduthelper.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZengZeHong on 2016/9/21.
 * 请求类
 */

public class PostBody {
    private static final String TAG = "PostBody";
    //请求参数
    private List<String> keys = new ArrayList<>();
    //请求数值
    private List<String> values = new ArrayList<>();

    public PostBody(Builder builder) {
        keys = builder.keys;
        values = builder.values;
    }

    public int size() {
        return keys.size();
    }

    private final class Builder {
        private final List<String> keys = new ArrayList<>();
        private final List<String> values = new ArrayList<>();

        public Builder() {
        }

        /**
         * 添加请求参数
         * @param key
         * @param value
         * @return
         */
        public Builder addParams(String key, String value) {
            if (keys != null && values != null) {
                keys.add(key);
                values.add(value);
            }
            return this;
        }

        public PostBody build() {
            return new PostBody(this);
        }
    }
}
