package com.yaohl0911.service;

import com.yaohl0911.entity.User;
import com.yaohl0911.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/findByName")
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
    @RequestMapping("/findById")
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @RequestMapping("/insertUser")
    public boolean insertUser(String name, Integer age) {
        int cnt = userMapper.insertUser(name, age);
        return cnt == 1 ? true : false;
    }
}
