package com.miller.seckill.service.impl;

import com.miller.seckill.mapper.TestUserMapper;
import com.miller.seckill.domain.TestUser;
import com.miller.seckill.service.TestUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by miller on 2018/8/8
 */
@Service
public class TestUserServiceImpl implements TestUserService {

    @Resource
    private TestUserMapper testUserMapper;


    @Override
    public TestUser getById(int id) {
        return testUserMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean tx() {
        TestUser tom = TestUser.builder().id(2).name("tom").build();
        TestUser miller = TestUser.builder().id(1).name("miller").build();
        testUserMapper.insert(tom);
        testUserMapper.insert(miller);
        return true;
    }
}
