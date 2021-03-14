package com.yaohl0911.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class FreemarkerIndexController {
    @RequestMapping("/freemarkerIndex")
    public String freemarkerIndex(Map<String, Object> result) {
        result.put("name", "jerry");
        result.put("sex", "0");
        result.put("age", 22);
        ArrayList<String> list = new ArrayList<>();
        list.add("yy");
        list.add("zz");
        list.add("dd");
        result.put("userList", list);
        // @Controller注解没加@ResponseBody注解，返回的是重定向
        return "freemarkerIndex";
    }
}
