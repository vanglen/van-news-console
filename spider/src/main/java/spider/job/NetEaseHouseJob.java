package spider.job;

import com.google.gson.Gson;
import common.util.HttpRequestUtil;
import model.spider.toutiao.ToutiaoFeedNewsApiResult;
import model.user.TNews;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 * 新闻首页
 * http://house.3g.163.com/bj/xf/web/news_list.html
 * 获取更多
 * http://house.3g.163.com/bj/xf/web/getCMSNewsList.html?callback=jQuery17206460106545047988_1502961749245&autoid=31&topicid=000782A4&start=16&size=15&_=1502961791692
 *
 * APP
 * 新闻首页
 * http://c.m.163.com/nc/article/house/5YyX5Lqs/0-20.html
 * 获取更多
 * https://c.m.163.com/nc/article/house/5YyX5Lqs/20-20.html
 * 获取详情
 * https://c.m.163.com/nc/article/CRT4B64F000788HN/full.html
 *
 *
 */
public class NetEaseHouseJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(ToutiaoJob.class);

    private static String api_url = "http://c.m.163.com/nc/article/house/5YyX5Lqs/0-20.html";


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

//    public static void getData() {
//        logger.info("开始读取url");
//        boolean has_more = false;
//        String result = "";
//        try {
//            result = HttpRequestUtil.sendGet(api_url, MessageFormat.format(api_params, max_behot_time));
//            logger.info("ApiResult:" + result);
//            if (result != null && !result.equals("")) {
//                Gson gson = new Gson();
//                ToutiaoFeedNewsApiResult apiResult = null;
//                try {
//                    apiResult = gson.fromJson(result, ToutiaoFeedNewsApiResult.class);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    logger.error("反序列化apiresult出错：" + e.toString());
//                }
//                if (apiResult != null && apiResult.getData().size() > 0) {
//                    has_more = apiResult.isHas_more();
//                    List<ToutiaoFeedNewsApiResult.ToutiaoFeedNewsData> listNewsData = apiResult.getData();
//                    for (ToutiaoFeedNewsApiResult.ToutiaoFeedNewsData newsData : listNewsData) {
//                        ToutiaoFeedNewsApiResult.ToutiaoFeedNewsItem newsItem = gson.fromJson(newsData.getContent(), ToutiaoFeedNewsApiResult.ToutiaoFeedNewsItem.class);
//                        if (newsItem.getTitle() != null && !newsItem.isHas_video()) {
//                            max_behot_time = newsItem.getBehot_time();
//
//                            //构造实体
//                            TNews tNews = new TNews();
//                            tNews.setTitle(newsItem.getTitle());
//                            if (newsItem.isHas_image()) {
//                                List<ToutiaoFeedNewsApiResult.ToutiaoFeedNewsItemImageItem> listImageItems = newsItem.getImage_list();
//                                if (listImageItems.size() > 0) {
//                                    tNews.setPic(listImageItems.get(0).getUrl());
//                                }
//                            } else {
//                                tNews.setPic("");
//                            }
//                            tNews.setSource(newsItem.getSource());
//                            tNews.setTags(newsItem.getTag());
//                            tNews.setType(newsItem.getArticle_type());
//                            tNews.setContent(GetArticleInfo(newsItem.getArticle_url()));
//                            tNews.setCountComment(0);
//                            tNews.setCountLike(0);
//                            tNews.setCountBrowser(0);
//                            tNews.setPublishTime(new Date(newsItem.getPublish_time()));
//                            tNews.setStatus(0);
//                            tNews.setCreatedtime(new Date());
//
//                            int resultDB = AddNews(tNews);
//                            current_num_today++;
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("抓取解析异常：" + e.getMessage() + "Result:" + result, e);
//        }
//    }
}
