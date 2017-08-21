package admin.controller;

import model.user.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.user.UserService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Resource
    private UserService userService;

    @RequestMapping("/index")
    public ModelAndView Index() {
        return new ModelAndView("account/index");
    }

    @ResponseBody
    @RequestMapping("/getuser")
    public TUser GetUserById(int id) {
        return userService.GetById(id);
    }
}
