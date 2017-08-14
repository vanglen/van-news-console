package webapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tab")
public class TabController {

    /**
     * 获取“我的”标签信息
     * @return
     */
    @ResponseBody
    @RequestMapping("my")
    public String GetMyTagInfo() {
        return "My Info!";
    }

    /**
     * 获取“资讯”标签信息
     * @return
     */
    @ResponseBody
    @RequestMapping("news")
    public String GetNewsTagInfo() {
        return "My Info!";
    }
}
