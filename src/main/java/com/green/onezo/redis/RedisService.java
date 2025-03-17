package com.green.onezo.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, String data){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, data);
    }

    public void setValue(String key, String data, Duration duration){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, data, duration);
    }

    @Transactional(readOnly = true)
    public String getValue(String key){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        if (valueOperations.get(key) == null) {
            return "데이터가 없음";
        }
        return (String) valueOperations.get(key);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }
    public void expireValues(String key, int timeout){
        redisTemplate.expire(key, timeout, TimeUnit.MICROSECONDS);
    }
    public void setHashOps(String key, Map<String, String> data){
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, data);
    }

    @Transactional(readOnly = true)
    public String getHashOps(String key, String hashKey) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        return Boolean.TRUE.equals(hashOperations.hasKey(key, hashKey)) ? (String) redisTemplate.opsForHash().get(key, hashKey) : "";
    }
    public void deleteHashOps(String key, String hashKey){
        HashOperations<String, Object, Object > hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hashKey);
    }

    public Boolean checkExistsValues(String value){
        return !value.equals("false");
    }

}
