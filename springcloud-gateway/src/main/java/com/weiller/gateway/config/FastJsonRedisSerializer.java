package com.weiller.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;

/**
 * redis序列化
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer {

    static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");


    @Nullable
    @Override
    public byte[] serialize(@Nullable Object t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        } else {
            return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
        }
    }

    @Nullable
    @Override
    public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
        if(bytes==null || bytes.length ==0){
            return null;
        }else{
            return JSON.parseObject(new String(bytes,DEFAULT_CHARSET),Object.class);
        }

    }
}
