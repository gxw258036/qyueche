package com.qu.web;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qu.application.UserTest;
import com.qu.conf.PublicConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.qu.entity.UserEntity;
import com.qu.mapper.UserMapper;

@RestController
@RequestMapping(value = PublicConfig.API_NAME + "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	private UserMapper userMapper;

    @Autowired
    private UserTest userTest;

    private static Logger logger  = Logger.getLogger(UserController.class);

    @GetMapping("/getUsers")
	public PageInfo getUsers() {
        Page<UserEntity> obj = PageHelper.startPage(1,2);
		List<UserEntity> users=userMapper.getAll();
        logger.info(users.size());
        PageInfo<UserEntity> page = new PageInfo<>(users);
        return page;
	}
	
    @GetMapping("/getUser")
    public UserEntity getUser(Long id) {
    	UserEntity user=userMapper.getOne(id);
        return user;
    }
    
    @PostMapping("/add")
    public void save(UserEntity user) {
    	userMapper.insert(user);
    }
    
    @PostMapping("/updated")
    public void update(UserEntity user) {
    	userMapper.update(user);
    }

    
    @GetMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userMapper.delete(id);
    }

    @GetMapping(value="/getUser/{id}")
    public UserEntity redisGetuser(@PathVariable("id") Long id) {
        return userTest.getUser(id);
    }
}