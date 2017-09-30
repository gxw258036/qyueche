package com.qu.application;

import com.alibaba.fastjson.JSON;
import com.qu.entity.UserEntity;
import com.qu.mapper.UserMapper;
import com.qu.util.RedisKeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guxiaowei on 2017/9/30.
 *
 * redis 查询demo
 */
@Service
public class UserTest {

    @Autowired
    private RedisService<Map> redisService;

    @Autowired
    private UserMapper userMapper;


    public UserEntity getUser(Long id){
        String key = RedisKeyPrefix.LOGIN_ROLE;
        boolean isExists = redisService.exists(key);
        UserEntity userEntity = null;
        if (isExists) {
            Map map = redisService.get(key, Map.class);
            if (map.containsKey(id)) {
                userEntity = JSON.parseObject(map.get(id).toString(), UserEntity.class);
            }
        }
        if (!isExists || userEntity == null) {
            Map map = new HashMap();
            userEntity = userMapper.getOne(id);
            map.put(id, userEntity);
            redisService.set(key, map);
        }
        return userEntity;
    }
}
