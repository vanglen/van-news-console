package webapi.controller;

import model.enums.EnumApiResultCode;
import model.param.ParamAccount;
import model.result.ResultAccount;
import model.result.ResultAccountLogin;
import model.result.ResultCommon;
import model.user.TUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.UserService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Resource
    private UserService userService;
    @Value("${app.url.newshost}")
    private String app_url_newshost;
    @Value("${app.url.userdefaultimg}")
    private String app_url_userdefaultimg;

    /**
     * 注册
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("register")
    public ResultCommon Register(ParamAccount paramAccount) {
        ResultCommon<ResultAccount> result = new ResultCommon<ResultAccount>();
        ResultAccount resultAccount = new ResultAccount();
        //check params
        if (paramAccount.getUsername() == null ||
                paramAccount.getUsername().length() == 0) {
            result.setMsg("用户名错误！");
        } else if (paramAccount.getPassword() == null ||
                paramAccount.getPassword().length() == 0) {
            result.setMsg("密码不能为空！");
        } else if (userService.ExistByUsername(paramAccount.getUsername())) {
            result.setMsg("用户已存在！");
        } else {
            TUser user = new TUser();
            user.setUsername(paramAccount.getUsername());
            user.setPassword(paramAccount.getPassword());
            int resultAdd = userService.Add(user);
            if (resultAdd > 0) {
                result.setCode(EnumApiResultCode.SUCCESS.getValue());
                result.setMsg("注册成功！");
                TUser resultUser = userService.GetByUsernameAndPassword(paramAccount.getUsername(), paramAccount.getPassword());
                resultAccount = ConvertUser2Account(resultUser);
            } else {
                result.setMsg("注册失败！");
            }
        }
        result.setData(resultAccount);
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
        ResultCommon<ResultAccount> result = new ResultCommon<ResultAccount>();
        ResultAccount resultAccount = new ResultAccount();

        //check params
        if (paramAccount.getUsername() == null ||
                paramAccount.getUsername().length() == 0 ||
                !userService.ExistByUsername(paramAccount.getUsername())) {
            result.setMsg("用户名错误！");
        } else if (paramAccount.getPassword() == null ||
                paramAccount.getPassword().length() == 0) {
            result.setMsg("密码不能为空！");
        } else {
            TUser resultUser = userService.GetByUsernameAndPassword(paramAccount.getUsername(), paramAccount.getPassword());
            if (resultUser != null && resultUser.getId() > 0) {
                result.setCode(EnumApiResultCode.SUCCESS.getValue());
                result.setMsg("登录成功！");
                resultAccount = ConvertUser2Account(resultUser);
            } else {
                result.setMsg("登录失败！");
            }
        }
        result.setData(resultAccount);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "modify",method = RequestMethod.POST)
    public ResultCommon Modify(ParamAccount paramAccount) {
        ResultCommon<ResultAccount> result = new ResultCommon<ResultAccount>();
        ResultAccount resultAccount = null;
        //验证参数
        //验证用户token
        if (!userService.VerifyUserToken(paramAccount.getUser_id(), paramAccount.getUser_token())) {
            result.setMsg("用户验证失败！");
        } else {
            TUser tUser = userService.GetById(paramAccount.getUser_id());
            tUser.setNickname(paramAccount.getNickname());
            tUser.setMobile(paramAccount.getMobile());
            tUser.setHeadpic(paramAccount.getHeadpic());
            tUser.setSex(paramAccount.getSex());
            tUser.setAddress(paramAccount.getAddress());
            int resultModify = userService.Modify(tUser);
            if (resultModify > 0) {
                result.setCode(EnumApiResultCode.SUCCESS.getValue());
                result.setMsg("更新成功！");
                TUser resultUser = userService.GetById(paramAccount.getUser_id());
                resultAccount = ConvertUser2Account(resultUser);
            } else {
                result.setMsg("更新失败！");
            }
        }

        result.setData(resultAccount);
        return result;
    }

    private ResultAccount ConvertUser2Account(TUser tUser) {
        ResultAccount resultAccount = null;
        if (tUser != null) {
            resultAccount = new ResultAccount();
            resultAccount.setUser_id(tUser.getId());
            resultAccount.setNickname(tUser.getNickname() == null ? "" : tUser.getNickname());
            resultAccount.setUsername(tUser.getUsername() == null ? "" : tUser.getUsername());
            resultAccount.setMobile(tUser.getMobile() == null ? "" : tUser.getMobile());
            resultAccount.setHeadpic(tUser.getHeadpic() == null ? app_url_newshost + app_url_userdefaultimg : tUser.getHeadpic());
            resultAccount.setSex(tUser.getSex() == null ? 0 : tUser.getSex());
            resultAccount.setAddress(tUser.getAddress() == null ? "" : tUser.getAddress());
            resultAccount.setUser_token(userService.GenerateUserToken(tUser));
        }
        return resultAccount;
    }
}
