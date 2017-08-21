package spider.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.util.*;
import common.util.Base64;
import model.spider.netease.NeteaseHouseNewsItem;
import model.news.TNews;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spider.provider.NewsProvider;

import java.text.MessageFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/8/17.
 * 新闻首页
 * http://house.3g.163.com/bj/xf/web/news_list.html
 * 获取更多
 * http://house.3g.163.com/bj/xf/web/getCMSNewsList.html?callback=jQuery17206460106545047988_1502961749245&autoid=31&topicid=000782A4&start=16&size=15&_=1502961791692
 * <p>
 * APP
 * 新闻首页
 * http://c.m.163.com/nc/article/house/5YyX5Lqs/0-20.html
 * 获取更多
 * https://c.m.163.com/nc/article/house/5YyX5Lqs/20-20.html
 * 获取详情
 * https://c.m.163.com/nc/article/CRT4B64F000788HN/full.html
 * 获取城市列表
 * http://c.m.163.com/nc/local/city.html
 */
public class NetEaseHouseJob implements Job {

    public NetEaseHouseJob() {
        mapCatalog.put("110000", "北京");
    }

    private static Logger logger = LoggerFactory.getLogger(ToutiaoJob.class);

    private static Map<String, String> mapCatalog = new HashMap<String, String>();
    private static int current = 0;
    private static int step = 20;
    private static int max_num_perday = 60;
    private static String api_url = "http://c.m.163.com/nc/article/house/{0}/{1}-20.html";
    private static String api_detail_url = "https://c.m.163.com/nc/article/{0}/full.html";

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Set<String> keys = mapCatalog.keySet();
        for (String key : keys) {
            do {
                getData(key, mapCatalog.get(key), current);
                current += step;
            } while (max_num_perday > current);
        }
    }

    public static void main(String[] args) {
        try {
            NetEaseHouseJob job = new NetEaseHouseJob();
            Set<String> keys = mapCatalog.keySet();
            for (String key : keys) {
                do {
                    String val= mapCatalog.get(key);
                    getData(Base64.encode(val), mapCatalog.get(key), current);
                    current += step;
                } while (max_num_perday > current);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void getData(String areaKey, String areaValue, int page) {
        logger.info("开始读取url");
        boolean has_more = false;
        String result = "";
        try {
            result = HttpRequestUtil.sendGet(MessageFormat.format(api_url, areaKey, page), "");
            logger.info("ApiResult:" + result);
            if (result != null && !result.equals("")) {
                JSONObject apiResult = JSON.parseObject(result);
                List<NeteaseHouseNewsItem> listNewsItem = JSON.parseArray(apiResult.get(areaValue).toString(), NeteaseHouseNewsItem.class);

                if (listNewsItem != null && listNewsItem.size() > 0) {
                    for (NeteaseHouseNewsItem newsItem : listNewsItem) {

                        //构造实体
                        TNews tNews = new TNews();
                        tNews.setTitle(newsItem.getTitle());
                        tNews.setPic(newsItem.getImgsrc());
                        tNews.setSource(newsItem.getSource());
                        tNews.setTags("");
                        tNews.setType(0);
                        tNews.setContent(GetArticleInfo(newsItem.getDocid()));
                        tNews.setCountComment(0);
                        tNews.setCountLike(0);
                        tNews.setCountBrowser(0);
                        tNews.setPublishTime(newsItem.getPtime());
                        tNews.setStatus(0);
                        tNews.setCreatedtime(new Date());

                        int resultDB = NewsProvider.AddNews(tNews);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("抓取解析异常：" + e.getMessage() + "Result:" + result, e);
        }
    }

    /**
     * 获取文章详情
     * 2016/10/14  14:30
     */
    private static String GetArticleInfo(String docId) {
        String url = MessageFormat.format(api_detail_url, docId);
        String content = "";
        if (url.length() > 0) {
            try {
                String result = HttpRequestUtil.sendGet(url, "");
                logger.info("ApiResult:" + result);
                if (result != null && !result.equals("")) {
                    JSONObject apiResult = JSON.parseObject(result);
                    Map doc = ((Map) apiResult.get(docId));
                    content = doc.get("body").toString();
                    //替换图片
                    if (doc.get("img") != null) {
                        JSONArray imgArray = JSON.parseArray(doc.get("img").toString());
                        Iterator<Object> imgs = imgArray.iterator();
                        while (imgs.hasNext()) {
                            JSONObject img = (JSONObject) imgs.next();
                            content = content.replace(img.getString("ref"), "<img src=\"" + img.getString("src") + "\" alt=\"" + img.getString("alt") + "\">");
                        }
                    }
//                    NeteaseHouseNewsDetail detail = JSON.parseObject(apiResult.get(docId).toString(), NeteaseHouseNewsDetail.class);
//                    if (detail != null) {
//                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("获取文章详情异常", ex);
            }
        }
        return content;
    }

}
