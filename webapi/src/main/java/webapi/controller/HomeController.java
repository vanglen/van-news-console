package webapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @ResponseBody
    @RequestMapping("/index")
    public String Index() {
        logger.error("test");
        return "index";
    }
}
