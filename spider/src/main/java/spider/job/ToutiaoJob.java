package spider.job;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import common.util.DateUtil;
import common.util.HttpRequestUtil;
import model.spider.toutiao.ToutiaoFeedNewsApiResult;
import model.news.TNews;
import org.apache.commons.lang.StringUtils;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16.
 */
public class ToutiaoJob implements Job {


    private static Logger logger = LoggerFactory.getLogger(ToutiaoJob.class);

    private static String api_url = "https://lf.snssdk.com/api/news/feed/v63/";
    private static String api_params = "category=news_house&count=20&max_behot_time={0}&city=%E5%8C%97%E4%BA%AC%E5%B8%82&cid=17960971&iid=13518918769";
    private static long max_behot_time = DateUtil.getNowTimeStamp();
    private static long max_num_perday = 100;//每天抓取最大数量
    private static int current_num_today = 0;//当天抓取数量

    private static String path_img = "images_news/" + DateUtil.getDateNow("yyyy-MM-dd") + "/";

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            //每天执行一次，每次重置参数
            max_behot_time = DateUtil.getNowTimeStamp();
            current_num_today = 0;
            do {
                getData();
            } while (max_num_perday > current_num_today);
        } catch (Exception e) {
            logger.error("ToutiaoJob执行出错：" + e.toString());
        }
    }

//    public static void main(String[] args0) {
//        getData();
//    }

    private static void getData() {
        logger.info("开始读取url");
        boolean has_more = false;
        String result = "";
        try {
            result = HttpRequestUtil.sendGet(api_url, MessageFormat.format(api_params, max_behot_time));
            logger.info("ApiResult:" + result);
            if (result != null && !result.equals("")) {
                Gson gson = new Gson();
                ToutiaoFeedNewsApiResult apiResult = null;
                try {
                    apiResult = gson.fromJson(result, ToutiaoFeedNewsApiResult.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("反序列化apiresult出错：" + e.toString());
                }
                if (apiResult != null && apiResult.getData().size() > 0) {
//                    has_more = apiResult.isHas_more();
                    List<ToutiaoFeedNewsApiResult.ToutiaoFeedNewsData> listNewsData = apiResult.getData();
                    for (ToutiaoFeedNewsApiResult.ToutiaoFeedNewsData newsData : listNewsData) {
                        ToutiaoFeedNewsApiResult.ToutiaoFeedNewsItem newsItem = gson.fromJson(newsData.getContent(), ToutiaoFeedNewsApiResult.ToutiaoFeedNewsItem.class);
                        if (newsItem.getTitle() != null && !newsItem.isHas_video()) {
                            max_behot_time = newsItem.getBehot_time();

                            //构造实体
                            TNews tNews = new TNews();
                            tNews.setTitle(newsItem.getTitle());
                            if (newsItem.isHas_image()) {
                                List<ToutiaoFeedNewsApiResult.ToutiaoFeedNewsItemImageItem> listImageItems = newsItem.getImage_list();
                                if (listImageItems.size() > 0) {
                                    tNews.setPic(listImageItems.get(0).getUrl());
                                }
                            } else {
                                tNews.setPic("");
                            }
                            tNews.setSource(newsItem.getSource());
                            tNews.setTags(newsItem.getTag());
                            tNews.setType(newsItem.getArticle_type());
                            tNews.setContent(GetArticleInfo(newsItem.getArticle_url()));
                            tNews.setCountComment(0);
                            tNews.setCountLike(0);
                            tNews.setCountBrowser(0);
                            tNews.setPublishTime(new Date(newsItem.getPublish_time()));
                            tNews.setStatus(0);
                            tNews.setCreatedtime(new Date());

                            int resultDB = NewsProvider.AddNews(tNews);
                            current_num_today++;
                        }
                    }
                }
            }
//            if (result != null && !result.equals("")) {
//                Gson gson = new Gson();
//                //从接口返回数据
//                TouTiaoApiResult apiResult;
//                try {
//                    apiResult = gson.fromJson(result, TouTiaoApiResult.class);
//                    if (apiResult != null && apiResult.getData().size() > 0) {
//                        long longTime = apiResult.getNext().getMax_behot_time();
//                        String date = Common.TimeSpanToDate(String.valueOf(longTime));
//                        int day = Common.differenceDay(date);
//                        if (day <= 20) {
//                            for (TouTiaoData item : apiResult.getData()) {
//                                if (!item.getHas_video()) {
//                                    //非画廊模式
//                                    if (item.getHas_gallery() != null && !item.getHas_gallery()) {
//                                        spider_news_info m = new spider_news_info();
//                                        String d1 = item.getDatetime();
//                                        m.setPublishTime(d1);//Common.GetSqlDate()
//                                        m.setTitle(item.getTitle());
//                                        m.setUrl(item.getSource_url());
//                                        m.setHas_video(0);
//                                        if (item.getVideo_duration_str() == null) {
//                                            m.setVideo_duration("");
//                                        } else {
//                                            m.setVideo_duration(item.getVideo_duration_str());
//                                        }
//
//                                        //单图模式去单张图片，多图为空
//                                        if (item.getMiddle_mode()) {
//                                            item.setPc_image_url(ImageUtil.GetWzImageUrl(item.getPc_image_url()));
//                                            m.setPicUrl(item.getPc_image_url());
//                                            m.setPicUrl_List("");
//                                        } else {
//                                            String imgs = "";
//                                            List<TouTiaoImages> list = item.getImage_list();
//                                            if (list.size() > 0) {
//                                                for (TouTiaoImages img : list) {
//                                                    img.setPc_url(ImageUtil.GetWzImageUrl(img.getPc_url()));
//                                                    imgs += img.getPc_url() + ",";
//                                                }
//                                                if (!imgs.equals("")) {
//                                                    imgs = imgs.substring(0, imgs.length() - 1);
//                                                    m.setPicUrl_List(imgs);
//                                                }
//                                                String img_url = (list.get(0)).getPc_url();
//                                                if (!img_url.equals("")) {
//                                                    m.setPicUrl(img_url);
//                                                }
//                                            }//多图模式下，去多图，默认图取第一张
//                                            else {
//                                                m.setPicUrl("");//单文本模式
//                                            }
//                                        }
//                                        //spider_news_info detailModel = GetArticleInfoNew(item.getSource_url(), item.getHas_video());
//                                        spider_news_info detailModel = GetArticleInfoFromMSite(item.getSource_url());
//                                        if (detailModel != null) {
//                                            if (detailModel.getMain() != null && detailModel.getSource() != null) {
//                                                m.setSource(detailModel.getSource());
//                                                m.setMain(detailModel.getMain());
//                                                m.setTags("");
//                                                m.setAuthor("");
//                                                m.setSummary("");
//                                                if (Common.PostData(m)) {
//                                                    Common.ShowMessage("成功同步一条数据：" + m.getTitle() + "来源:" + m.getSource());
//                                                }
//                                            }
//                                        }
//
//                                    } else {
//                                        Common.ShowMessage("发现画廊模式的：标题：" + item.getTitle());
//                                    }
//                                }
//
//                            }
//                            if (apiResult.getHas_more().equals("1")) {
//                                String urlAgain = String.format(api_url2, key, longTime);
//                                Common.ShowMessage("下一个hotTime:" + longTime);
//                                GetData(key, urlAgain);
//                            }
//                        }
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Common.ShowMessage(e.getMessage());
//                }
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("抓取解析异常：" + e.getMessage() + "Result:" + result, e);
        }
    }

    /**
     * 获取文章详情
     * 2016/10/14  14:30
     */
    private static String GetArticleInfo(String url) {
        String content = "";
        if (url != null && url.length() > 0) {
            try {
                Document document = Jsoup.connect(url).get();

                String jsonStr = StringUtils.substringBetween(document.html(), "var BASE_DATA =", ";");
                Map parse = (Map) JSONObject.parse(jsonStr);

            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("获取文章详情异常", ex);
            }
        }
        return content;
    }

}
