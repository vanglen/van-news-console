package webapi.controller;

import model.param.ParamAccount;
import model.result.ResultAccountLogin;
import model.result.ResultCommon;
import model.user.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.UserService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Resource
    private UserService userService;

    /**
     * 注册
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("register")
    public ResultCommon Register(ParamAccount paramAccount) {
        ResultCommon<String> result = new ResultCommon<String>();

        //check params
        if (paramAccount.getUsername() == null ||
                paramAccount.getUsername().length() == 0 ||
                userService.ExistByUsername(paramAccount.getUsername())) {
            result.setCode(-1);
            result.setMsg("用户名错误！");
        } else if (paramAccount.getPassword() == null ||
                paramAccount.getPassword().length() == 0) {
            result.setCode(-2);
            result.setMsg("密码不能为空！");
        } else {
            TUser user = new TUser();
            user.setUsername(paramAccount.getUsername());
            user.setPassword(paramAccount.getPassword());
            int resultAdd = userService.Add(user);
            if (resultAdd > 0) {
                result.setCode(1);
                result.setMsg("注册成功！");
            } else {
                result.setCode(-3);
                result.setMsg("注册失败！");
            }
        }
        result.setData("");
        return result;
    }

    /**
     * 登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("login")
    public ResultCommon Login(ParamAccount paramAccount) {
        ResultCommon<ResultAccountLogin> result = new ResultCommon<ResultAccountLogin>();
        ResultAccountLogin resultAccountLogin = new ResultAccountLogin();
        resultAccountLogin.setToken("");

        //check params
        if (paramAccount.getUsername() == null ||
                paramAccount.getUsername().length() == 0 ||
                !userService.ExistByUsername(paramAccount.getUsername())) {
            result.setCode(-1);
            result.setMsg("用户名错误！");
        } else if (paramAccount.getPassword() == null ||
                paramAccount.getPassword().length() == 0) {
            result.setCode(-2);
            result.setMsg("密码不能为空！");
        } else {
            TUser user = new TUser();
            user.setUsername(paramAccount.getUsername());
            user.setPassword(paramAccount.getPassword());
            TUser resultUser = userService.GetByUsernameAndPassword(user.getUsername(), user.getPassword());
            if (resultUser != null && resultUser.getId() > 0) {
                result.setCode(1);
                result.setMsg("登录成功！");
                resultAccountLogin.setToken(userService.GenerateUserToken(resultUser));
            } else {
                result.setCode(-3);
                result.setMsg("登录失败！");
            }
        }
        result.setData(resultAccountLogin);
        return result;
    }

    @ResponseBody
    @RequestMapping("modify")
    public ResultCommon Modify(ParamAccount paramAccount) {
        ResultCommon<String> result = new ResultCommon<String>();

        //验证参数
        //验证用户token
        if (userService.VerifyUserToken(paramAccount.getUser_id(), paramAccount.getUser_token())) {
            TUser tUser = userService.GetById(paramAccount.getUser_id());
            tUser.setNickname(paramAccount.getNickname());
            tUser.setMobile(paramAccount.getMobile());
            tUser.setHeadpic(paramAccount.getHeadpic());
            int resultModify = userService.Modify(tUser);
            if (resultModify > 0) {
                //失败
                result.setCode(1);
                result.setMsg("更新成功！");
            } else {
                result.setCode(-1);
                result.setMsg("更新失败！");
            }
        }

        result.setData("");
        return result;
    }
}
