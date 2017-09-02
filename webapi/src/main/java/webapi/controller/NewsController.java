package webapi.controller;

import model.enums.EnumApiResultCode;
import model.news.TNews;
import model.newsarea.TNewsArea;
import model.param.ParamBase;
import model.param.ParamTabNews;
import model.result.ResultCommon;
import model.result.ResultNewsArea;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.news.NewsAreaService;
import service.news.NewsService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;
    @Resource
    private NewsAreaService newsAreaService;

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

    @ResponseBody
    @RequestMapping("city")
    public ResultCommon ListNewsCities(ParamBase paramBase) {
        ResultCommon<List<ResultNewsArea>> result = new ResultCommon<List<ResultNewsArea>>();
        List<ResultNewsArea> resultNewsAreaList = new ArrayList<ResultNewsArea>();
        List<TNewsArea> tNewsAreaList = newsAreaService.ListNewsAreaUsable();
        if (tNewsAreaList != null && tNewsAreaList.size() > 0) {
            for (TNewsArea item : tNewsAreaList) {
                ResultNewsArea resultNewsArea = new ResultNewsArea();
                resultNewsArea.setCity_id(item.getId());
                resultNewsArea.setCity_name(item.getName());
                resultNewsArea.setFirst_letter(item.getFirstLetter());
                resultNewsArea.setArea_id(item.getAreaId());

                resultNewsAreaList.add(resultNewsArea);
            }
            result.setCode(EnumApiResultCode.SUCCESS.getValue());
        } else {
            result.setCode(EnumApiResultCode.FAILED.getValue());
        }

        result.setData(resultNewsAreaList);
        return result;
    }
}
