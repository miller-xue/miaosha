package com.miller.seckill.service;

import com.miller.seckill.domain.TestUser;

/**
 * Created by miller on 2018/8/8
 */
public interface TestUserService {

    TestUser getById(int id);

    boolean tx();
}
