package spider.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/8/14.
 */
public class TestJob implements Job {
    Logger logger = LoggerFactory.getLogger(Job.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("excute test job!");
    }


//
//    public static void main(String[] args) {
//        AutoVideoJob.invoke();
//    }
//
//    private static Logger logger = Logger.getLogger(AutoVideoJob.class);
//
//    /**
//     * 获取抓取数据天数。当为0时抓取所有数据。
//     */
//    private static int InDay = 2;
//    /**
//     * 5.0抓取的视屏的标签。包含汽车之家原创23、工能演示21、聊车评车1、新车5、改装/性能28、事故/车祸3、美女2、用车养车29、碰撞测试17
//     */
////    private static List<Integer> tagArray = Arrays.asList(23, 21, 1, 5, 28, 3, 2, 29, 17);
//    private static Map<Integer, String> tagMap = new HashMap<Integer, String>();
//
//    static {
//        tagMap.put(23, "汽车之家原创");
//        tagMap.put(21, "工能演示");
//        tagMap.put(1, "聊车评车");
//        tagMap.put(5, "新车");
//        tagMap.put(28, "改装/性能");
//        tagMap.put(3, "事故/车祸");
//        tagMap.put(2, "美女");
//        tagMap.put(29, "用车养车");
//        tagMap.put(17, "碰撞测试");
//    }
//
//
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        invoke();
//    }
//
//    private static void invoke() {
//        for (Integer tag : tagMap.keySet()) {
//            int PageStart = 1;
//            int pageSize = 100;
//            int totalCount;
//            do {
//                totalCount = getData(tag, tagMap.get(tag), PageStart, pageSize);
//                PageStart += pageSize;
//            } while (PageStart <= totalCount);
//        }
//    }
//
//    /**
//     * @param tagId
//     * @param start 开始位置，从1开始
//     * @param count 返回数量
//     * @return
//     */
//    private static int getData(int tagId, String tagName, int start, int count) {
//        int totalCount = 0;
//        //视频
//        int biz_type = 15;
//        if (tagId > 0 && start > 0 && count > 0) {
//            String url = "http://v.api.lq.autohome.com.cn/Wcf/VideoService.svc/GetVideosByTag";
//            String param = "tagId={0}&start={1}&count={2}&orderType=PublishTimeDesc&returnVr=False&_appid=cms";
//            String result = HttpRequest.sendGet(url, MessageFormat.format(param, String.valueOf(tagId), String.valueOf(start), String.valueOf(count)));
//            if (!result.equals("")) {
//                Gson gson = new Gson();
//                VideoApiResult videoApiResult;
//                try {
//                    videoApiResult = gson.fromJson(result, new TypeToken<VideoApiResult>() {
//                    }.getType());
//                    if (videoApiResult != null && videoApiResult.getResult() != null && videoApiResult.getResult().getTotalcount() > 0) {
//                        totalCount = videoApiResult.getResult().getTotalcount();
//                        List<VideoItem> videoItemList = videoApiResult.getResult().getItems();
//                        //验证本批数据是否需要抓取
//                        if (!checkSpiderDate(videoItemList)) {
//                            //若没有需要抓取的数据，则返回数量0
//                            totalCount = 0;
//                        } else {
//                            for (VideoItem videoItem : videoItemList) {
//                                AutoHomeNews model = new AutoHomeNews();
//                                model.setBiz_id(videoItem.getId());
//                                model.setBiz_type(biz_type);
//                                model.setTitle(videoItem.getTitle());
//                                model.setAuthor(videoItem.getAuthor().getNickname());
//                                model.setCms_content_class(tagName);
//                                model.setCms_series_id("");
//                                model.setPlaycount(videoItem.getPlaycount());
//                                if (videoItem.getTags().size() > 0) {
//                                    ArrayList<String> listTags = new ArrayList<String>();
//                                    for (VideoTag tag : videoItem.getTags()) {
//                                        listTags.add(tag.getName());
//                                    }
//                                    model.setCms_tags(Common.listToString(listTags, ','));
//                                }
//                                model.setContent(videoItem.getDescription());
//                                model.setImg_url(Common.GetAutoHomeImageSrc(videoItem.getCoverimage2()));
//                                model.setGraphic_img_list2("");
//                                model.setIndex_detail(videoItem.getTitle());
//                                model.setImg_url2("");
//                                model.setPublish_time(Common.GetSqlTimeSpan(Common.timeStampWithZoneToString(videoItem.getPublishtime())));
//                                model.setDuration(String.valueOf(videoItem.getDuration()));
//                                model.setVideoid(videoItem.getVideoid());
//                                model.setPlayurls(getMediaInfos(videoItem.getVideoid()));
//                                int addResult = AutoHomeNewsHelper.Add(model);
//                                if (addResult > 0) {
//                                    logger.info(videoItem.getTitle() + "入库成功");
//                                } else {
//                                    logger.error(videoItem.getTitle() + "入库失败");
//                                }
//                            }
//                        }
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    logger.error("AutoVideoJob-getData:" + ex.toString());
//                }
//            }
//        }
//        return totalCount;
//    }
//
//    private static boolean checkSpiderDate(List<VideoItem> list) {
//        boolean result = false;
//        if (list != null && list.size() > 0) {
//            for (VideoItem videoItem : list) {
//                try {
//                    Date d1 = Common.getSpiderDate(InDay);
//                    Date d2 = Common.GetSqlTimeSpan(Common.timeStampWithZoneToString(videoItem.getPublishtime()));
//                    if (d2.compareTo(d1) > 0) {
//                        result = true;
//                        break;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    logger.error("AutoVideoJob-checkSpiderDate:" + e.toString());
//                    break;
//                }
//            }
//        }
//        return result;
//    }
//
//    private static String getMediaInfos(String vid) {
//        String rtn = "";
//        String url = "http://p.vp.autohome.com.cn/api/mpi";
//        String param = "mid={0}&ct=hd";
//        String result = HttpRequest.sendGet(url, MessageFormat.format(param, String.valueOf(vid)));
//        if (result != null && result.length() > 0) {
//            Gson gson = new Gson();
//            MediaApiResult mediaApiResult;
//            try {
//                mediaApiResult = gson.fromJson(result, new TypeToken<MediaApiResult>() {
//                }.getType());
//                if (mediaApiResult != null && mediaApiResult.getMediainfos() != null && mediaApiResult.getMediainfos().size() > 0) {
//                    //直接返回json数据
//                    rtn = result;
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                logger.error("AutoVideoJob-getMediaInfos:" + ex.toString());
//            }
//        }
//        return rtn;
//    }
}
