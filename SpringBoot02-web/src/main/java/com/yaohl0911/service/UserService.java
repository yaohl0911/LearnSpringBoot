package com.yaohl0911.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService {
    @Value("${User.name}")
    private String name;
    @Value("${User.age}")
    private int age;

    @RequestMapping("/properties")
    public String properties() {
        return name + " - " + age;
    }
}
