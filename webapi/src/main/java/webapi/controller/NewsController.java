package webapi.controller;

import model.news.TNews;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.news.NewsService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    @RequestMapping("/detail")
    public ModelAndView Detail(int news_id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("news/detail");
        if (news_id > 0) {
            TNews news = newsService.GetById(news_id);
            modelAndView.addObject("news", news);
        }
        return modelAndView;
    }

    /**
     * 获取评论列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/newscommentlist")
    public String ListNewsComment() {
        return "News Comment List";
    }

    /**
     * 发表评论
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/newscommentadd")
    public String AddNewsComment() {
        return "News Comment List";
    }

    /**
     * 点赞
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/like")
    public String AddLike() {
        return "News Comment List";
    }
}
