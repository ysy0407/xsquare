/**
 * JacksonUtil.java
 *
 * Copyright 2011 Baidu, Inc.
 *
 * Baidu licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.xsquare.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public abstract class JacksonUtil {
    // can reuse, share globally
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        // 如果为空则不输出
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 对于空的对象转json的时候不抛出错误
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 禁用序列化日期为timestamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 禁用遇到未知属性抛出异常
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 视空字符传为null
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    public static final String obj2Str(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }

    public static final byte[] obj2Bytes(Object o) throws IOException {
        return mapper.writeValueAsBytes(o);
    }

    public static final void writeObj(OutputStream out, Object value) throws IOException {
        mapper.writeValue(out, value);
    }

    public static final <T> List<T> str2List(String s, Class<T> valueType) throws IOException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, valueType);
        return mapper.readValue(s, javaType);
    }

    public static final JsonNode readTree(InputStream in) throws IOException {
        return mapper.readTree(in);
    }

    public static final JsonNode readTree(String jsonStr) throws IOException {
        return mapper.readTree(jsonStr);
    }

    public static final <T> T readObj(InputStream in, Class<T> valueType) throws IOException {
        return mapper.readValue(in, valueType);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T readObj(InputStream in, JavaType valueType) throws IOException {
        return (T) mapper.readValue(in, valueType);
    }

    public static final <T> T str2Obj(String s, Class<T> valueType) throws IOException {
        return mapper.readValue(s, valueType);
    }

}
