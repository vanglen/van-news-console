package webapi.controller;

import common.util.DateUtil;
import model.comment.TComment;
import model.enums.EnumApiResultCode;
import model.enums.EnumCommentType;
import model.news.TNews;
import model.param.ParamCommentAdd;
import model.param.ParamCommentList;
import model.result.ResultCommentList;
import model.result.ResultCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.comment.CommentService;
import service.news.NewsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    private Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private NewsService newsService;
    @Resource
    private CommentService commentService;
    @Value("${app.url.newshost}")
    private String app_url_newshost;
    @Value("${app.url.userdefaultimg}")
    private String app_url_userdefaultimg;

    @ResponseBody
    @RequestMapping("publish")
    public ResultCommon Add(ParamCommentAdd paramCommentAdd) {
        ResultCommon<String> resultCommon = new ResultCommon<String>();

        //TODO: 验证用户token

        if (paramCommentAdd.getTo_id() <= 0 ||
                paramCommentAdd.getContent() == null ||
                paramCommentAdd.getContent().length() <= 0) {
            resultCommon.setMsg("参数错误！");
        } else {
            TComment tComment = new TComment();
            tComment.setToId(paramCommentAdd.getTo_id());
            tComment.setToType(paramCommentAdd.getTo_type());
            tComment.setContent(paramCommentAdd.getContent());
            tComment.setUserId(paramCommentAdd.getUser_id());
            tComment.setStatus(1);
            tComment.setCreatedtime(new Date());
            int resultAdd = commentService.add(tComment);
            if (resultAdd > 0) {
                resultCommon.setCode(EnumApiResultCode.SUCCESS.getValue());
                resultCommon.setMsg("评论成功！");
            }
        }

        resultCommon.setData("");
        return resultCommon;
    }

    @ResponseBody
    @RequestMapping("list")
    public ResultCommon List4Page(ParamCommentList paramCommentList) {
        ResultCommon<ResultCommentList<ResultCommentList.ResultCommentNewsItem>> resultCommon
                = new ResultCommon<ResultCommentList<ResultCommentList.ResultCommentNewsItem>>();
        ResultCommentList<ResultCommentList.ResultCommentNewsItem> resultCommentList
                = new ResultCommentList<ResultCommentList.ResultCommentNewsItem>();

        if (paramCommentList.getTo_id() <= 0) {
            resultCommon.setMsg("参数错误！");
        } else {
            if (paramCommentList.getLast_comment_timestamp() <= 0) {
                paramCommentList.setLast_comment_timestamp(new Date().getTime());
            }
            if (paramCommentList.getCount() <= 0) {
                paramCommentList.setCount(10);
            }

            Date last_comment_datetime = new Date(paramCommentList.getLast_comment_timestamp());

            if (paramCommentList.getTo_type() == EnumCommentType.ARTICLE.getValue()) {
                ResultCommentList<ResultCommentList.ResultCommentNewsItem>.ResultCommentNewsItem newsItem
                        = resultCommentList.new ResultCommentNewsItem();
                //获取资讯信息
                TNews tNews = newsService.GetById(paramCommentList.getTo_id());
                if (tNews != null) {
                    newsItem.setNews_id(tNews.getId());
                    newsItem.setCount_like(tNews.getCountLike());
                    newsItem.setCount_browser(tNews.getCountBrowser());
                    newsItem.setCount_comment(tNews.getCountComment());
                }
                resultCommentList.setTo_data(newsItem);
            }

            List<ResultCommentList<ResultCommentList.ResultCommentNewsItem>.ResultCommentItem> comment_data
                    = resultCommentList.getComment_data();
            List<TComment> commentList = commentService.select4Page(paramCommentList.getCount(), last_comment_datetime);
            for (TComment comment : commentList) {
                ResultCommentList.ResultCommentItem resultCommentItem = resultCommentList.new ResultCommentItem();
                resultCommentItem.setComment_id(comment.getId() == null ? 0 : comment.getId());
                resultCommentItem.setComment_content(comment.getContent() == null ? "" : comment.getContent());
                resultCommentItem.setComment_datetime(DateUtil.getDateTime(comment.getCreatedtime(), "yyyy-MM-dd HH:mm:ss"));
                resultCommentItem.setComment_is_self((paramCommentList.getUser_id() == (comment.getUserId() == null ? 0 : comment.getUserId())) ? 1 : 0);
                resultCommentItem.setTo_id(comment.getToId() == null ? 0 : comment.getToId());
                resultCommentItem.setTo_type(comment.getToType() == null ? 0 : comment.getToType());
                resultCommentItem.setUser_id(comment.getUserId() == null ? 0 : comment.getUserId());
                resultCommentItem.setUser_name(comment.getUserName() == null ? "" : comment.getUserName());
//                resultCommentItem.setUser_headpic(comment.getUserHeadpic());
                resultCommentItem.setUser_headpic(app_url_newshost + app_url_userdefaultimg);

                comment_data.add(resultCommentItem);

                resultCommentList.setLast_comment_timestamp(comment.getCreatedtime().getTime());
            }

            resultCommentList.setComment_data(comment_data);
            resultCommentList.setCount(comment_data.size());

            resultCommon.setCode(EnumApiResultCode.SUCCESS.getValue());
            resultCommon.setMsg("获取评论成功！");
        }

        resultCommon.setData(resultCommentList);
        return resultCommon;
    }
}
