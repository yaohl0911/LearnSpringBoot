package yaohl0911.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloService {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
