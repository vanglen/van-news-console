package spider.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.util.HttpRequestUtil;
import model.news.TNews;
import model.spider.sohu.SohuMuyingNewsItem;
import model.spider.sohu.SohuMuyingNewsTags;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spider.provider.NewsProvider;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/11.
 * <p>
 * 列表接口：https://v2.sohu.com/public-api/feed?scene=CHANNEL&sceneId=26&page=2&size=20&_=1505098959370
 * 详情接口：http://www.sohu.com/a/191068769_111870
 */
public class SohuMuyingJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(SohuMuyingJob.class);
    private static int page = 1;
    private static int size = 20;
    private static long timestamp = System.currentTimeMillis();
    private static String url_api = "https://v2.sohu.com/public-api/feed?scene=CHANNEL&sceneId=26&page={0}&size={1}&_={2}";
    private static String url_api_detail = "https://www.sohu.com/a/{0}";

    //获取列表中是否包含今天的资讯，若包含则可以继续请求。
    private static boolean has_today = true;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }

    public static void main(String[] args) {
        page = 1;
        size = 5;
        timestamp = System.currentTimeMillis();
        getDataNewsList(page, size, timestamp);
    }

    private static void getDataNewsList(int page, int size, long timestamp) {
        String url = MessageFormat.format(url_api, String.valueOf(page), String.valueOf(size), String.valueOf(timestamp));
        String api_result = "";
        try {
            api_result = HttpRequestUtil.sendGet(url, "");
            if (api_result != null && !api_result.equals("")) {
                List<SohuMuyingNewsItem> listNewsItem = JSON.parseArray(api_result, SohuMuyingNewsItem.class);
                if (listNewsItem != null && listNewsItem.size() > 0) {
                    for (SohuMuyingNewsItem newsItem : listNewsItem) {

                        //构造实体
                        TNews tNews = new TNews();
                        tNews.setTitle(newsItem.getTitle());
                        tNews.setDigest(newsItem.getMobileTitle());
                        tNews.setPic(newsItem.getPicUrl());
                        tNews.setSource(newsItem.getAuthorName());
                        List<SohuMuyingNewsTags> listNewsTags = newsItem.getTags();
                        if (listNewsTags != null && listNewsTags.size() > 0) {
                            tNews.setTags("母婴," + listNewsTags.get(0).getName());
                        } else {
                            tNews.setTags("母婴");
                        }
                        tNews.setType(1);
                        tNews.setCategoryId(0);
                        tNews.setCategoryName("母婴");
                        tNews.setCityId(0);
                        tNews.setCityAreaId("");
                        tNews.setCityName("");
                        String doc_id = String.valueOf(newsItem.getId()) + "_" + String.valueOf(newsItem.getAuthorId());
                        tNews.setContent(GetArticleInfo(doc_id));
                        tNews.setCountComment(0);
                        tNews.setCountLike(0);
                        tNews.setCountBrowser(0);
                        Date publishTime = new Date(Long.valueOf(newsItem.getPublicTime()));
                        tNews.setPublishTime(publishTime);
                        tNews.setCheckTime(publishTime);
                        tNews.setStatus(1);
                        tNews.setSourceDocid(doc_id);
                        tNews.setSourceUrl(MessageFormat.format(url_api_detail, doc_id));
                        tNews.setSourceWebsite("sohu");
                        tNews.setCreatedtime(new Date());

//                            logger.info("添加资讯到数据库开始。");
                        int resultDB = NewsProvider.AddNews(tNews);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("抓取解析异常：" + e.getMessage() + "Result:" + api_result, e);
        }
    }

    /**
     * 获取文章详情
     * 2016/10/14  14:30
     */
    private static String GetArticleInfo(String docId) {
        String url = MessageFormat.format(url_api_detail, docId);
        String content = "";
        if (url.length() > 0) {
            try {
                String result = HttpRequestUtil.sendGet(url, "");
                //logger.info("ApiResult:" + result);
                if (result != null && !result.equals("")) {
                    Document document = Jsoup.parse(result);
                    content = document.getElementsByTag("article ").outerHtml();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("获取文章详情异常", ex);
            }
        }
        return content;
    }
}
