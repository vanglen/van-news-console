package webapi.controller;

import model.user.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.UserService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String Hello(String name) {
        return userService.hello(name);
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public TUser GetUserByid(int id) {
        return userService.getById(id);
    }
}
