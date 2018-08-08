package com.miller.seckill.service.impl;

import com.miller.seckill.dao.UserMapper;
import com.miller.seckill.domain.User;
import com.miller.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by miller on 2018/8/8
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public User getById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean tx() {
        User tom = User.builder().id(2).name("tom").build();
        User miller = User.builder().id(1).name("miller").build();
        userMapper.insert(tom);
        userMapper.insert(miller);
        return true;
    }
}
