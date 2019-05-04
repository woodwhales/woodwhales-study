package org.woodwhales.king.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodwhales.king.entity.User;
import org.woodwhales.king.mapper.UserMapper;
import org.woodwhales.king.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String userName) {
    	log.debug("query user by username = [{}]", userName);
    	return userMapper.findUserByUsername(userName);
    }
}
