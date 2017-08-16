package spider.job;

import com.google.gson.Gson;
import common.util.DateUtil;
import common.util.DbHelper;
import common.util.HttpRequestHelper;
import model.spider.toutiao.ToutiaoFeedNewsApiResult;
import model.user.TNews;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public class ToutiaoJob implements Job {

    private static String driverClassName = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/van_news";
    private static String user = "root";
    private static String password = "root";
    private static Logger logger = LoggerFactory.getLogger(ToutiaoJob.class);

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            //
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private static String api_url = "https://lf.snssdk.com/api/news/feed/v63/?category=news_house&count=20&max_behot_time={0}&city=%E5%8C%97%E4%BA%AC%E5%B8%82&cid=17960971&iid=13518918769";
    private static long max_behot_time = DateUtil.getNowTimeStamp();

//    public static void main(String[] args0) {
//        getData();
//    }

    public static void getData() {
        logger.info("开始读取url");
        String result = "";
        try {
            result = HttpRequestHelper.sendGet(MessageFormat.format(api_url, max_behot_time), "");
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
                    List<ToutiaoFeedNewsApiResult.ToutiaoFeedNewsData> listNewsData = apiResult.getData();
                    for (ToutiaoFeedNewsApiResult.ToutiaoFeedNewsData newsData : listNewsData) {
                        ToutiaoFeedNewsApiResult.ToutiaoFeedNewsItem newsItem = gson.fromJson(newsData.getContent(), ToutiaoFeedNewsApiResult.ToutiaoFeedNewsItem.class);
                        max_behot_time = newsItem.getBehot_time();

                        //构造实体
                        TNews tNews = new TNews();
                        tNews.setTitle(newsItem.getTitle());
                        tNews.setPic("");
                        tNews.setSource(newsItem.getSource());
                        tNews.setTags(newsItem.getTag());
                        tNews.setType(newsItem.getArticle_type());
                        tNews.setContent("");
                        tNews.setCountComment(0);
                        tNews.setCountLike(0);
                        tNews.setCountBrowser(0);
                        tNews.setPublishTime(new Date(newsItem.getPublish_time()));
                        tNews.setStatus(0);
                        tNews.setCreatedtime(new Date());

                        int resultDB = AddNews(tNews);
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
//                                            item.setPc_image_url(ImageHelper.GetWzImageUrl(item.getPc_image_url()));
//                                            m.setPicUrl(item.getPc_image_url());
//                                            m.setPicUrl_List("");
//                                        } else {
//                                            String imgs = "";
//                                            List<TouTiaoImages> list = item.getImage_list();
//                                            if (list.size() > 0) {
//                                                for (TouTiaoImages img : list) {
//                                                    img.setPc_url(ImageHelper.GetWzImageUrl(img.getPc_url()));
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

//    /**
//     * 从M站获取头条内容
//     *
//     * @param url m站地址
//     * @return spider_news_info
//     */
//    private static spider_news_info GetArticleInfoFromMSite(String url) {
//        spider_news_info model = new spider_news_info();
//        url = url.replace("www", "m").replace("tem/", "") + "info/";
//        String result = HttpRequest.sendGet(url, "");
//        if (!result.equals("")) {
//            try {
//                Gson gson = new Gson();
//                TouTiaoInfoResult touTiaoInfoResult = gson.fromJson(result, new TypeToken<TouTiaoInfoResult>() {
//                }.getType());
//                if (touTiaoInfoResult != null) {
//                    model.setSource(touTiaoInfoResult.getData().getSource());
//                    String Info = touTiaoInfoResult.getData().getContent();
//                    Document document = Jsoup.parse(Info);
//                    Element element = document.select("div").first();
//                    ImageHelper.updateContentImage(element);
//                    model.setMain(element.html());
//                }
//            } catch (Exception e) {
//                logger.error("GetArticleInfoFromMSite错误：" + e.getMessage());
//            }
//        }
//        return model;
//    }


//    /**
//     * 获取文章详情
//     * 2016/10/14  14:30
//     */
//    private static spider_news_info GetArticleInfoNew(String url, Boolean hasVideo) {
//        spider_news_info model = new spider_news_info();
//        try {
//            Document document = Jsoup.connect(url).get();
//            if (hasVideo) {
//                Elements links = document.select(".detail-main");
//                if (links.size() > 0) {
//                    for (Element e : links) {
//                        ImageHelper.updateContentImage(e);
//                        Element es = e.select(".video-wrap").get(0);
//                        String main = es.html().replace('\'', '‘');
//                        model.setMain(main);
//                        String source = e.select(".name").text();
//                        model.setSource("今日头条-" + source);
//                    }
//                }
//            } else {
//                Elements links = document.select("#article-main");
//                if (links.size() > 0) {
//                    for (Element item : links) {
//                        ImageHelper.updateContentImage(item);
//                        String source = item.select(".articleInfo .src").text();
//                        model.setSource("今日头条-" + source);
//                        Element es = item.select(".article-content").get(0);
//                        es.select("script").remove();
//                        model.setMain(es.html().replace('\'', '‘'));
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            logger.error("获取文章详情异常", ex);
//        }
//        return model;
//    }

    private static int AddNews(TNews model) {
        int result = 0;
        if (model != null && !model.getTitle().equals("")) {
            String strTitle = model.getTitle().replace("?", "？");
            String STR_SQL_FIND_TITLE = "SELECT id from t_news with(nolock) where title ='" + strTitle + "' limit 1";
            Connection conn = new DbHelper(driverClassName, url, user, password).getConnection();
            QueryRunner queryRunner = new QueryRunner();
            try {
                List<TNews> list = queryRunner.query(conn, STR_SQL_FIND_TITLE, new BeanListHandler<TNews>(TNews.class));
                if (list == null || list.size() == 0) {
                    String STR_SQL_ADD = "INSERT INTO `van_news`.`t_news`\n" +
                            "(`title`,\n" +
                            "`pic`,\n" +
                            "`source`,\n" +
                            "`tags`,\n" +
                            "`type`,\n" +
                            "`content`,\n" +
                            "`count_comment`,\n" +
                            "`count_like`,\n" +
                            "`count_browser`,\n" +
                            "`publish_time`,\n" +
                            "`check_time`,\n" +
                            "`status`,\n" +
                            "`createdtime`)\n" +
                            "VALUES\n" +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?);\n";
                    Object[] params = {model.getTitle(), model.getPic(), model.getSource(), model.getTags(),
                            model.getType(), model.getContent(), model.getCountComment(), model.getCountLike(), model.getCountBrowser(),
                            model.getPublishTime(), model.getCheckTime(), model.getStatus(), model.getCreatedtime()};
                    try {
                        result = queryRunner.update(conn, STR_SQL_ADD, params);
                        DbUtils.closeQuietly(conn);
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                    } finally {
                        DbUtils.closeQuietly(conn);
                    }
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
        return result;
    }
}
