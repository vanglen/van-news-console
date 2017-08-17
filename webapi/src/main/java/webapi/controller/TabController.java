package webapi.controller;

import model.param.ParamMyTab;
import model.result.ResultCommon;
import model.result.ResultMyTag;
import model.user.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.UserService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/tab")
public class TabController {

    @Resource
    private UserService userService;

    /**
     * 获取“我的”标签信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("my")
    public ResultCommon GetMyTagInfo(ParamMyTab paramMyTab) {
        ResultCommon<ResultMyTag> result = new ResultCommon<ResultMyTag>();
        ResultMyTag myTag = new ResultMyTag();
        myTag.setUserid(0);
        myTag.setUsername("请登陆");
        myTag.setHeadpic("/pic/default.jpg");
        result.setData(myTag);

        if (paramMyTab.getUserid() > 0 &&
                paramMyTab.getToken() != null &&
                paramMyTab.getToken().length() > 0) {

            //根据ID获取用户信息
            TUser tUser = userService.GetById(paramMyTab.getUserid());
            //验证token
            String userToken = userService.GenerateUserToken(tUser);
            if (!userToken.equalsIgnoreCase(paramMyTab.getToken())) {
                result.setCode(-1);
                result.setMsg("非法用户！");
            } else {
                result.getData().setUserid(tUser.getId());
                result.getData().setUsername(tUser.getUsername());
                if (tUser.getHeadpic() != null && tUser.getHeadpic().length() > 0)
                    result.getData().setHeadpic(tUser.getHeadpic());
            }
        }


        return result;
    }

    /**
     * 获取“资讯”标签信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("news")
    public String GetNewsTagInfo() {
        return "My Info!";
    }
}
