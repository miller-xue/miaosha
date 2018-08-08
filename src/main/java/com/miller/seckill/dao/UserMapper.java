package com.miller.seckill.dao;

import com.miller.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by miller on 2018/8/8
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User selectById(@Param("id") int id);

    @Insert("insert into user(id,name) values(#{id},#{name})")
    int insert(User user);
}
