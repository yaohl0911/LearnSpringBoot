package com.yaohl0911.controller;

import com.yaohl0911.entity.ThymeleafEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class ThymeleafController {
    @RequestMapping("/thymeleaf")
    public String myThymeleaf(Map<String, Object> result) {
        result.put("user", new ThymeleafEntity("tom", 12));
        ArrayList<ThymeleafEntity> list = new ArrayList<>();
        list.add(new ThymeleafEntity("yy", 30));
        list.add(new ThymeleafEntity("tt", 29));
        list.add(new ThymeleafEntity("dd", 1));
        result.put("list", list);
        return "thymeleaf";
    }
}

