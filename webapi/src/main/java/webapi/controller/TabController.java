package webapi.controller;

import common.util.DateUtil;
import model.news.TNews;
import model.param.ParamTabMy;
import model.param.ParamTabNews;
import model.result.ResultCommon;
import model.result.ResultTabMy;
import model.result.ResultTabNews;
import model.user.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.news.NewsService;
import service.user.UserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tab")
public class TabController {

    @Resource
    private UserService userService;
    @Resource
    private NewsService newsService;

    /**
     * 获取“我的”标签信息
     *
     * @param paramTabMy paramTabMy
     * @return ResultCommon
     */
    @ResponseBody
    @RequestMapping("my")
    public ResultCommon GetMyTagInfo(ParamTabMy paramTabMy) {
        ResultCommon<ResultTabMy> result = new ResultCommon<ResultTabMy>();
        ResultTabMy myTag = new ResultTabMy();
        myTag.setUsername("请登陆");
        myTag.setHeadpic("img/userdefaultheadpic.jpg");

        if (paramTabMy.getUser_id() > 0 &&
                paramTabMy.getUser_token() != null &&
                paramTabMy.getUser_token().length() > 0) {

            //根据ID获取用户信息
            TUser tUser = userService.GetById(paramTabMy.getUser_id());
            //验证token
            String userToken = userService.GenerateUserToken(tUser);
            if (!userToken.equalsIgnoreCase(paramTabMy.getUser_token())) {
                result.setMsg("非法用户！");
            } else {
                result.setCode(1);

                myTag.setUserid(tUser.getId());
                myTag.setUsername(tUser.getUsername() == null ? "" : tUser.getUsername());
                myTag.setNickname(tUser.getNickname() == null ? "" : tUser.getNickname());
                myTag.setMobile(tUser.getMobile() == null ? "" : tUser.getMobile());
                myTag.setSex(tUser.getSex() == null ? 0 : tUser.getSex());
                myTag.setAddress(tUser.getAddress() == null ? "" : tUser.getAddress());
//                if (tUser.getHeadpic() != null && tUser.getHeadpic().length() > 0)
//                    myTag.setHeadpic(tUser.getHeadpic());
            }
        }

        result.setData(myTag);
        return result;
    }

    /**
     * 获取“资讯”标签信息
     *
     * @param paramTabNews paramTabNews
     * @return ResultCommon
     */
    @ResponseBody
    @RequestMapping("news")
    public ResultCommon GetNewsTagInfo(ParamTabNews paramTabNews) {
        ResultCommon<ResultTabNews> result = new ResultCommon<ResultTabNews>();
        ResultTabNews newsTab = new ResultTabNews();

        if (paramTabNews.getLast_timestamp() <= 0) {
            paramTabNews.setLast_timestamp(new Date().getTime());
        }
        if (paramTabNews.getCount() <= 0) {
            paramTabNews.setCount(20);
        }

        Date max_check_time = new Date(paramTabNews.getLast_timestamp());

        List<TNews> newsList = newsService.ListByCheckTime(paramTabNews.getCount(), max_check_time);
        for (TNews news : newsList) {
            ResultTabNews.ResultTabNewsItem item = newsTab.new ResultTabNewsItem();
            item.setCount_browser(news.getCountBrowser());
            item.setCount_comment(news.getCountComment());
            item.setCount_like(news.getCountLike());
            item.setNews_id(news.getId());
            item.setTitle(news.getTitle());
            item.setNews_time(DateUtil.getDateTime(news.getCheckTime(), "yyyy-MM-dd HH:mm:ss"));
            item.setNews_timestamp(news.getCheckTime().getTime());
            item.setPic(news.getPic());
            item.setSource(news.getSource());
            item.setTags(news.getTags());
            item.setOpenschama("news/detail.do?news_id=" + news.getId());

            newsTab.setLast_news_timestamp(news.getCheckTime().getTime());

            newsTab.getNews_data().add(item);
        }

        result.setCode(1);
        result.setData(newsTab);
        return result;
    }
}
