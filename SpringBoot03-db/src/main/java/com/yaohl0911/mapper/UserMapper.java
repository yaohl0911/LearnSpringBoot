package com.yaohl0911.mapper;

import com.yaohl0911.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE name = #{name}")
    User findByName(@Param("name") String name);
    @Select("SELECT * FROM USERS WHERE id = #{id}")
    User findById(@Param("id") Integer id);
    @Insert("INSERT INTO users (name, age) VALUES (#{name}, #{age});")
    int insertUser(@Param("name") String name, @Param("age") Integer age);
}
