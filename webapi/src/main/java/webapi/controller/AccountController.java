package webapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/account")
public class AccountController {

    /**
     * 注册
     * @return
     */
    @ResponseBody
    @RequestMapping("register")
    public String Register() {
        return "Register Action";
    }

    /**
     * 登录
     * @return
     */
    @ResponseBody
    @RequestMapping("login")
    public String Login() {
        return "Login Action";
    }
}
