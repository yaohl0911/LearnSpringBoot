package com.yaohl0911.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RegisterService {
    @Autowired
    private SmsServiceAsync smsServiceAsync;

    @RequestMapping("/userRegister")
    public boolean userRegister() {
        addUser();
        smsServiceAsync.smsAsync();
        return true;
    }

    public boolean addUser() {
        log.info(">01<");
        try {
            log.info(">插入数据..<");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(">04<");
        return true;
    }
}