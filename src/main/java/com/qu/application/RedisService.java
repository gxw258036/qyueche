package com.qu.application;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by guxiaowei on 2017/9/30.
 */
@Service
public class RedisService<TDto> {
    @Autowired
    private RedisTemplate redisTemplate;

    // 批量删除对应的value
    public void deleteAll(String... keys) {
        for (String key : keys) {
            delete(key);
        }
    }

    // 批量删除key
    public void deletePattern(String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    // 删除指定key的value
    public void delete(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    // 判断缓存中是否有对应的value
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    // 读取缓存
    public TDto get(String key, Class<TDto> clz) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        Object result = operations.get(key);
        if (result != null) {
            return JSON.parseObject(result.toString(), clz);
        }
        return null;
    }

    // 写入缓存
    public boolean set(String key, Object value) {
        boolean flag = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, JSON.toJSONString(value));
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 写入缓存
    public boolean set(String key, Object value, Long expireTime) {
        boolean flag = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
