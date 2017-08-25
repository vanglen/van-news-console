package webapi.controller;

import common.util.DateUtil;
import model.enums.EnumApiResultCode;
import model.news.TNews;
import model.param.ParamTabMy;
import model.param.ParamTabNews;
import model.result.ResultCommon;
import model.result.ResultTabMy;
import model.result.ResultTabNews;
import model.user.TUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.news.NewsService;
import service.user.UserService;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tab")
public class TabController {

    @Resource
    private UserService userService;
    @Resource
    private NewsService newsService;
    @Value("${app.url.newshost}")
    private String app_url_newshost;
    @Value("${app.url.userdefaultimg}")
    private String app_url_userdefaultimg;
    @Value("${app.url.newsdetail}")
    private String app_url_newsdetail;
    @Value("${app.url.openschemapre}")
    private String app_url_openschemapre;

    private Logger logger = LoggerFactory.getLogger(TabController.class);

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
        myTag.setHeadpic(app_url_newshost + app_url_userdefaultimg);

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
                result.setCode(EnumApiResultCode.SUCCESS.getValue());

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

        if (paramTabNews.getLast_news_timestamp() <= 0) {
            paramTabNews.setLast_news_timestamp(new Date().getTime());
        }
        if (paramTabNews.getCount() <= 0) {
            paramTabNews.setCount(20);
        }

        Date last_check_time = new Date(paramTabNews.getLast_news_timestamp());

        List<TNews> newsList = newsService.ListByCheckTime(paramTabNews.getCount(), last_check_time);
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
            String openschema = null;
            String url = MessageFormat.format(app_url_newshost + app_url_newsdetail, news.getId());
            item.setUrl(url);
            try {
                openschema = MessageFormat.format(app_url_openschemapre, URLEncoder.encode(url, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.error("openshema编码出错：" + e.getMessage(), e);
            }
            item.setOpenschema(openschema);

            newsTab.setLast_news_timestamp(news.getCheckTime().getTime());

            newsTab.getNews_data().add(item);
        }

        newsTab.setCount(newsTab.getNews_data().size());
        result.setCode(EnumApiResultCode.SUCCESS.getValue());
        result.setData(newsTab);
        return result;
    }
}
