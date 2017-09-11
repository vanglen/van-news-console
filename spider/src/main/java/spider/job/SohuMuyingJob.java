package spider.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.util.HttpRequestUtil;
import model.spider.sohu.SohuMuyingNewsItem;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 * <p>
 * 列表接口：https://v2.sohu.com/public-api/feed?scene=CHANNEL&sceneId=26&page=2&size=20&_=1505098959370
 * 详情接口http://www.sohu.com/a/191068769_111870
 */
public class SohuMuyingJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(SohuMuyingJob.class);
    private static int page = 1;
    private static int size = 20;
    private static long timestamp = System.currentTimeMillis();
    private static String url_api = "https://v2.sohu.com/public-api/feed?scene=CHANNEL&sceneId=26&page={0}&size={1}&_={2}";

    //获取列表中是否包含今天的资讯，若包含则可以继续请求。
    private static boolean has_today = true;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }

    public static void main(String[] args) {

    }

    private static void getDataNewsList(int page, int size, long timestamp) {
        String url = MessageFormat.format(url_api, String.valueOf(page), String.valueOf(size), String.valueOf(timestamp));
        String api_result = "";
        try {
            api_result = HttpRequestUtil.sendGet(url, "");
            if (api_result != null && !api_result.equals("")) {
                List<SohuMuyingNewsItem> listNewsItem = JSON.parseArray(api_result, SohuMuyingNewsItem.class);
                if (listNewsItem != null && listNewsItem.size() > 0) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("抓取解析异常：" + e.getMessage() + "Result:" + api_result, e);
        }
    }
}
