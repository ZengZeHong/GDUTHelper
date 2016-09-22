package com.zzh.gdut.gduthelper.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZengZeHong on 2016/9/21.
 * 请求体
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

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public int size() {
        return keys.size();
    }

    public static final class Builder {
        private final List<String> keys = new ArrayList<>();
        private final List<String> values = new ArrayList<>();

        public Builder() {
        }

        /**
         * 添加请求参数
         *
         * @param key
         * @param value
         * @return
         */
        public Builder addParams(String key, String value) {
            //为空则报空指针
            if (key == null || value == null)
                throw new NullPointerException();
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
