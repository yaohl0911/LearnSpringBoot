package com.yaohl0911.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsServiceAsync {
    @Async("taskExecutor") // 开启异步注解并指定线程池
    public String smsAsync() {
        log.info(">02<");
        try {
            log.info(">正在发送短信..<");
            Thread.sleep(3000);
        } catch (Exception e) {

        }
        log.info(">03<");
        return "短信发送完成!";
    }
}
