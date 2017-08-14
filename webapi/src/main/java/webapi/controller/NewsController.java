package webapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/news")
public class NewsController {

    /**
     * 获取评论列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/newscommentlist")
    public String ListNewsComment(){
        return "News Comment List";
    }

    /**
     * 发表评论
     * @return
     */
    @ResponseBody
    @RequestMapping("/newscommentadd")
    public String AddNewsComment(){
        return "News Comment List";
    }

    /**
     * 点赞
     * @return
     */
    @ResponseBody
    @RequestMapping("/like")
    public String AddLike(){
        return "News Comment List";
    }
}
