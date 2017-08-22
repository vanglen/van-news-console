package spider.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.util.*;
import model.newsarea.TNewsArea;
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
import java.util.stream.Collectors;

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
    private static String api_url_city = "http://c.m.163.com/nc/local/city.html";

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            //更新城市列表
            mapCatalog = getDataCity();
            Set<String> keys = mapCatalog.keySet();
            for (String key : keys) {
                do {
                    getDataNews(key, mapCatalog.get(key), current);
                    current += step;
                } while (max_num_perday > current);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("NetEaseHouseJob Error:" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            getDataCity();
//            NetEaseHouseJob job = new NetEaseHouseJob();
//            Set<String> keys = mapCatalog.keySet();
//            for (String key : keys) {
//                do {
//                    String val = mapCatalog.get(key);
//                    getDataNews(val, mapCatalog.get(key), current);
//                    current += step;
//                } while (max_num_perday > current);
//            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static List<TNewsArea> getCityUsable() {
        return NewsProvider.ListNewsAreaUsable();
    }

    private static Map<String, String> getDataCity() {
        Map<String, String> result = new HashMap<>();
        //获取当前城市列表
        List<TNewsArea> tNewsAreaList = NewsProvider.ListNewsAreaUsable();
        //抓取远程城市列表
        String apiResult = HttpRequestUtil.sendGet(api_url_city, "");
        if (apiResult != null && !apiResult.equals("")) {
            JSONObject apiJsonObj = JSON.parseObject(apiResult);
            JSONArray apiCitys = (JSONArray) apiJsonObj.get("cities");
            if (apiCitys != null && apiCitys.size() > 0) {
                Iterator<Object> citys = apiCitys.iterator();
                while (citys.hasNext()) {
                    Map apiLetterCitys = (Map) citys.next();
                    if (apiLetterCitys != null && apiLetterCitys.size() > 0) {
                        Iterator<String> cityKeys = apiLetterCitys.keySet().iterator();
                        while (cityKeys.hasNext()) {
                            String cityKey = cityKeys.next();
                            JSONArray letterCitys = (JSONArray) apiLetterCitys.get(cityKey);
                            Iterator<Object> letterCity = letterCitys.iterator();
                            while(letterCity.hasNext()){
                                JSONObject element  = (JSONObject)letterCity.next();
                                String str_city_name = element.get("c").toString();
                                String str_city_area_id = element.get("m").toString();
                                boolean bo_city_status = element.containsKey("h") && Boolean.parseBoolean(element.get("h").toString());

                                List<TNewsArea> filterNewsAreaList = tNewsAreaList.stream().filter(x -> x.getAreaId().equals(str_city_area_id)).collect(Collectors.toList());
                                //添加城市
                                if (filterNewsAreaList == null || filterNewsAreaList.size() == 0) {
                                    TNewsArea tNewsArea = new TNewsArea();
                                    tNewsArea.setAreaId(str_city_area_id);
                                    tNewsArea.setName(str_city_name);
                                    tNewsArea.setStatus(bo_city_status ? 1 : 0);
                                    tNewsArea.setCreatedtime(new Date());
                                    tNewsArea.setFirstLetter(String.valueOf(cityKey));
                                    int resultAddNewsArea = NewsProvider.AddNewsArea(tNewsArea);
                                    if (resultAddNewsArea <= 0) {
                                        logger.info("保存城市失败！城市信息：" + letterCity.toString());
                                    }
                                }
                            }
                        }
                    }
                }
                //重新查询本地城市列表
                tNewsAreaList = NewsProvider.ListNewsAreaUsable();
            }
        }

        if (tNewsAreaList != null && tNewsAreaList.size() > 0) {
            for (TNewsArea tNewsArea : tNewsAreaList) {
                result.put(tNewsArea.getAreaId(), tNewsArea.getName());
            }
        }

        return result;
    }

    private static void getDataNews(String areaKey, String areaValue, int page) {
        logger.info("开始读取url");
        boolean has_more = false;
        String result = "";
        try {
            result = HttpRequestUtil.sendGet(MessageFormat.format(api_url, Common.encodeBase64(areaValue, "utf-8"), page), "");
            logger.info("ApiResult:" + result);
            if (result != null && !result.equals("")) {
                JSONObject apiResult = JSON.parseObject(result);
                List<NeteaseHouseNewsItem> listNewsItem = JSON.parseArray(apiResult.get(areaValue).toString(), NeteaseHouseNewsItem.class);

                if (listNewsItem != null && listNewsItem.size() > 0) {
                    for (NeteaseHouseNewsItem newsItem : listNewsItem) {

                        //构造实体
                        TNews tNews = new TNews();
                        tNews.setTitle(newsItem.getTitle());
                        tNews.setDigest(newsItem.getDigest());
                        tNews.setPic(newsItem.getImgsrc());
                        tNews.setSource(newsItem.getSource());
                        tNews.setTags("房产");
                        tNews.setType(10);
                        tNews.setCategoryId(0);
                        tNews.setCategoryName("房产");
                        tNews.setCityAreaId(areaKey);
                        tNews.setCityName(areaValue);
                        tNews.setContent(GetArticleInfo(newsItem.getDocid()));
                        tNews.setCountComment(0);
                        tNews.setCountLike(0);
                        tNews.setCountBrowser(0);
                        tNews.setPublishTime(newsItem.getPtime());
                        tNews.setStatus(0);
                        tNews.setSourceDocid(newsItem.getDocid());
                        tNews.setSourceUrl(newsItem.getUrl());
                        tNews.setSourceWebsite("163");
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
