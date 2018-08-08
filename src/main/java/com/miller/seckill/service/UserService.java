package com.miller.seckill.service;

import com.miller.seckill.domain.User;

/**
 * Created by miller on 2018/8/8
 */
public interface UserService {

    User getById(int id);

    boolean tx();
}
