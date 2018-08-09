package com.miller.seckill.dao;

import com.miller.seckill.domain.TestUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by miller on 2018/8/8
 */
@Mapper
public interface TestUserMapper {

    @Select("select * from test_user where id = #{id}")
    TestUser selectById(@Param("id") int id);

    @Insert("insert into test_user(id,name) values(#{id},#{name})")
    int insert(TestUser user);
}
