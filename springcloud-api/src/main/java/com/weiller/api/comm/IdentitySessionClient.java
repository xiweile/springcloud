package com.weiller.api.comm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * 身份session客户端
 */
@Component
public class IdentitySessionClient {


    public static final String TOKEN_KEY = "token";

    public static final String SESSION_ID_KEY = "sessonid";

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Value("${session.redis.duetime}")
    private Integer duetime ;

    public Object getSessionInfo( String sessionid) {
        return redisTemplate.opsForValue().get(sessionid);
    }

    public void setSession(String sessionId,String sessionInfo){
        Boolean hasKey = redisTemplate.hasKey(sessionId);
        if(!hasKey.booleanValue()){
            redisTemplate.opsForValue().set(sessionId,sessionInfo,duetime, TimeUnit.MINUTES);
        }
    }

    public void removeSession(String sessionId){
        redisTemplate.delete(sessionId);
    }

}
