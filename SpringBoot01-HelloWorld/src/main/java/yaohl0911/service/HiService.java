package yaohl0911.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HiService {
    @RequestMapping("/hi")
    @ResponseBody
    public String Hi() {
        return "Hi";
    }
}
