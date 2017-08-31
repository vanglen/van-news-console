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

    private static Logger logger = LoggerFactory.getLogger(ToutiaoJob.class);

    private static Map<String, String> mapCatalog = new HashMap<String, String>();
    private static List<TNewsArea> listCities = new ArrayList<>();
    private static int current = 0;
    private static int step = 20;
    private static int max_num_perday_percity = 60;
    //抓取几天内的数据
    private static int max_day = 1;
    //抓取的数据中资讯最早的时间
    private static Date min_datetime = null;
    private static String api_url = "http://c.m.163.com/nc/article/house/{0}/{1}-20.html";
    private static String api_detail_url = "https://c.m.163.com/nc/article/{0}/full.html";
    private static String api_url_city = "http://c.m.163.com/nc/local/city.html";
    private static Map<String, Object> map_log = new HashMap<>();
    private static List<String> listBoardid = new ArrayList<>();

    public NetEaseHouseJob() {
        mapCatalog.put("110000", "北京");
        init_map_log();
        init_list_boardid();
    }

    private void init_map_log() {
        map_log.clear();
        map_log.put("city_id", 0);
        map_log.put("city_id", "");
        map_log.put("city_name", "");
        map_log.put("city_code", "");
        map_log.put("http_request_ok", 0);
        map_log.put("http_request_failed", 0);
        map_log.put("db_ok", 0);
        map_log.put("db_failed", 0);
    }

    private void init_list_boardid() {
        if (listBoardid != null && listBoardid.size() <= 0) {
            listBoardid.add("house_bbs");
            listBoardid.add("gzhouse_bbs");
            listBoardid.add("health3_bbs");

        }
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            Date now = new Date();
            min_datetime = new Date();
            //更新城市列表
            listCities = getDataCityUsable();
            if (listCities != null && listCities.size() > 0) {
                for (TNewsArea area : listCities) {
                    map_log.put("city_id", area.getId());
                    map_log.put("city_areaid", area.getAreaId());
                    map_log.put("city_name", area.getName());
                    map_log.put("city_code", Common.encodeBase64(area.getName(), "utf-8"));
                    do {
                        getDataNews(area, current);
                        current += step;
                    } while ((now.getTime() - min_datetime.getTime() < 86400000)
                            && max_num_perday_percity > current);
                    current = 0;
                    //打印统计信息
                    logger.info("城市-" + area.getName() + "：" + JSON.toJSONString(map_log));
                    init_map_log();
                }
//                Set<String> keys = mapCatalog.keySet();
//                for (String key : keys) {
//                    map_log.put("city_id", key);
//                    map_log.put("city_name", mapCatalog.get(key));
//                    map_log.put("city_code", Common.encodeBase64(mapCatalog.get(key), "utf-8"));
//                    do {
//                        getDataNews(key, mapCatalog.get(key), current);
//                        current += step;
//                    } while ((now.getTime() - min_datetime.getTime() < 86400000)
//                            && max_num_perday_percity > current);
//                    current = 0;
//                    //打印统计信息
//                    logger.info("城市：" + mapCatalog.get(key) + "抓取信息：" + JSON.toJSONString(map_log));
//                    init_map_log();
//                }
            } else {
                logger.info("抓取城市列表为空。");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("NetEaseHouseJob Error:" + ex.getMessage(), ex);
        }
    }

    public static void main(String[] args) {
        try {
            getDataCityUsable();
//            NetEaseHouseJob job = new NetEaseHouseJob();
//            Set<String> keys = mapCatalog.keySet();
//            for (String key : keys) {
//                do {
//                    String val = mapCatalog.get(key);
//                    getDataNews(val, mapCatalog.get(key), current);
//                    current += step;
//                } while (max_num_perday_percity > current);
//            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static List<TNewsArea> getDataCityUsable() {
        List<TNewsArea> tNewsAreaList = NewsProvider.ListNewsAreaUsable();
        if (tNewsAreaList == null || tNewsAreaList.size() <= 0) {
            logger.info("抓取城市列表为空。");
            logger.info("获取抓取城市列表开始。163");
            getDataCityRemote();
            logger.info("获取抓取城市列表结束。163");
            //重新查询
            tNewsAreaList = NewsProvider.ListNewsAreaUsable();
        }
        return tNewsAreaList;
//        return ConvertCityList2Map(tNewsAreaList);
    }

    private static void getDataCityRemote() {
        //获取当前城市列表
        List<TNewsArea> tNewsAreaList = NewsProvider.ListNewsAreaAll();
        if (tNewsAreaList == null || tNewsAreaList.size() <= 0) {
            //抓取远程城市列表
            logger.info("获取远程城市列表开始。163");
            String apiResult = HttpRequestUtil.sendGet(api_url_city, "");
            logger.info("获取远程城市列表结束。163。结果：" + apiResult);
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
                                while (letterCity.hasNext()) {
                                    JSONObject element = (JSONObject) letterCity.next();
                                    String str_city_name = element.get("c").toString();
                                    String str_city_area_id = element.get("m").toString();
                                    boolean bo_city_status = element.containsKey("h") && Boolean.parseBoolean(element.get("h").toString());
                                    //logger.info("getDataCity TRACE:" + str_city_area_id + JSON.toJSONString(tNewsAreaList));
                                    List<TNewsArea> filterNewsAreaList = (tNewsAreaList == null || tNewsAreaList.size() <= 0) ? null : tNewsAreaList.stream().filter(x -> x.getAreaId().equals(str_city_area_id)).collect(Collectors.toList());
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
                }
            }
        }
    }

    private static Map<String, String> ConvertCityList2Map(List<TNewsArea> tNewsAreaList) {
        Map<String, String> result = new HashMap<>();
        if (tNewsAreaList != null && tNewsAreaList.size() > 0) {
            for (TNewsArea tNewsArea : tNewsAreaList) {
                if (tNewsArea.getStatus().equals(1)) {
                    result.put(tNewsArea.getAreaId(), tNewsArea.getName());
                }
            }
        }
        return result;
    }

    private static void getDataNews(TNewsArea area, int page) {
        String url = MessageFormat.format(api_url, Common.encodeBase64(area.getName(), "utf-8"), String.valueOf(page));
//        logger.info("开始读取url:" + url);
        boolean has_more = false;
        String result = "";
        try {
//            logger.info("请求资讯列表开始163。");
            result = HttpRequestUtil.sendGet(url, "");
//            logger.info("请求资讯列表结束163。返回字节：" + result.length());
            map_log.put("http_request_ok", ((int) map_log.get("http_request_ok")) + 1);
//            logger.info("ApiResult:" + result);
            if (result != null && !result.equals("")) {
                JSONObject apiResult = JSON.parseObject(result);
                List<NeteaseHouseNewsItem> listNewsItem = JSON.parseArray(apiResult.get(area.getName()).toString(), NeteaseHouseNewsItem.class);

                if (listNewsItem != null && listNewsItem.size() > 0) {
                    for (NeteaseHouseNewsItem newsItem : listNewsItem) {

                        if (min_datetime.compareTo(newsItem.getPtime()) > 0) {
                            min_datetime = newsItem.getPtime();
                        }

                        if (listBoardid.contains(newsItem.getBoardid()) ||
                                "doc".equals(newsItem.getSkipType())) {
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
                            tNews.setCityId(area.getId());
                            tNews.setCityAreaId(area.getAreaId());
                            tNews.setCityName(area.getName());
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

//                            logger.info("添加资讯到数据库开始。");
                            int resultDB = NewsProvider.AddNews(tNews);
//                            logger.info("添加资讯到数据库结束。结果：" + resultDB);
                            if (resultDB > 0) {
                                map_log.put("db_ok", ((int) map_log.get("db_ok") + 1));
                            } else {
                                map_log.put("db_failed", ((int) map_log.get("db_failed") + 1));
                            }
                        }
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
                //logger.info("ApiResult:" + result);
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
