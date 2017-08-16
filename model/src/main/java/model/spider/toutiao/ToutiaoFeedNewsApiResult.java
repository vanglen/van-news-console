package model.spider.toutiao;

import sun.misc.FloatingDecimal;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 * api eg:
 * https://lf.snssdk.com/api/news/feed/v63/?category=news_house&concern_id=6215497897127971330&refer=1&count=20&max_behot_time=1502827736&last_refresh_sub_entrance_interval=1502854375&loc_mode=7&loc_time=1502852300&latitude=39.98187&longitude=116.308159&city=%E5%8C%97%E4%BA%AC%E5%B8%82&tt_from=pre_load_more&lac=4566&cid=17960971&cp=5991923bb4ce7q1&plugin_enable=3&iid=13518918769&device_id=37181337637&ac=wifi&channel=xiaomi&aid=13&app_name=news_article&version_code=631&version_name=6.3.1&device_platform=android&ab_version=154254%2C160531%2C158054%2C159918%2C158954%2C151125%2C161070%2C157002%2C157237%2C161097%2C162398%2C134128%2C158531%2C162404%2C162010%2C162333%2C161078%2C162260%2C152026%2C162217%2C125174%2C162235%2C162572%2C161014%2C156262%2C161643%2C160809%2C157295%2C161928%2C31211%2C156827%2C131207%2C162059%2C145585%2C162159%2C161380%2C161615%2C162573%2C161720%2C151116&ab_client=a1%2Cc4%2Ce1%2Cf2%2Cg2%2Cf7&ab_feature=102749%2C94563&abflag=3&ssmix=a&device_type=Redmi+Note+4X&device_brand=xiaomi&language=en&os_api=23&os_version=6.0.1&uuid=866135035363669&openudid=5e448117b691b0da&manifest_version_code=631&resolution=1080*1920&dpi=480&update_version_code=6310&_rticket=1502854375191&plugin=2431
 */
public class ToutiaoFeedNewsApiResult {

    //region Member
    private String message;
    private List<ToutiaoFeedNewsData> data;
    private int total_number;
    private boolean has_more;
    //endregion

    //region Getter and Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ToutiaoFeedNewsData> getData() {
        return data;
    }

    public void setData(List<ToutiaoFeedNewsData> data) {
        this.data = data;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }
    //endregion

    public class ToutiaoFeedNewsData{

        //region Member
        private String code;
        private String content;
        //endregion

        //region Getter and Setter
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
        //endregion
    }

    public class ToutiaoFeedNewsItem {

        //region Member
        private String _abstract;
        private int article_sub_type;
        private int article_type;
        private String article_url;
        private long behot_time;
        private int cell_flag;
        private int cell_layout_style;
        private int cell_type;
        private String display_url;
        private int gallary_image_count;
        private String group_id;
        private boolean has_image;
        private boolean has_video;
        private String item_id;
        private String keywords;
        private List<ToutiaoFeedNewsItemImageItem> image_list;
        private long publish_time;
        private String rid;
        private String source;
        private String tag;
        private long tag_id;
        private String title;
        private String url;
        //endregion

        //region Getter and Setter
        public String get_abstract() {
            return _abstract;
        }

        public void set_abstract(String _abstract) {
            this._abstract = _abstract;
        }

        public int getArticle_sub_type() {
            return article_sub_type;
        }

        public void setArticle_sub_type(int article_sub_type) {
            this.article_sub_type = article_sub_type;
        }

        public int getArticle_type() {
            return article_type;
        }

        public void setArticle_type(int article_type) {
            this.article_type = article_type;
        }

        public String getArticle_url() {
            return article_url;
        }

        public void setArticle_url(String article_url) {
            this.article_url = article_url;
        }

        public long getBehot_time() {
            return behot_time;
        }

        public void setBehot_time(long behot_time) {
            this.behot_time = behot_time;
        }

        public int getCell_flag() {
            return cell_flag;
        }

        public void setCell_flag(int cell_flag) {
            this.cell_flag = cell_flag;
        }

        public int getCell_layout_style() {
            return cell_layout_style;
        }

        public void setCell_layout_style(int cell_layout_style) {
            this.cell_layout_style = cell_layout_style;
        }

        public int getCell_type() {
            return cell_type;
        }

        public void setCell_type(int cell_type) {
            this.cell_type = cell_type;
        }

        public String getDisplay_url() {
            return display_url;
        }

        public void setDisplay_url(String display_url) {
            this.display_url = display_url;
        }

        public int getGallary_image_count() {
            return gallary_image_count;
        }

        public void setGallary_image_count(int gallary_image_count) {
            this.gallary_image_count = gallary_image_count;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public boolean isHas_image() {
            return has_image;
        }

        public void setHas_image(boolean has_image) {
            this.has_image = has_image;
        }

        public boolean isHas_video() {
            return has_video;
        }

        public void setHas_video(boolean has_video) {
            this.has_video = has_video;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public List<ToutiaoFeedNewsItemImageItem> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<ToutiaoFeedNewsItemImageItem> image_list) {
            this.image_list = image_list;
        }

        public long getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(long publish_time) {
            this.publish_time = publish_time;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public long getTag_id() {
            return tag_id;
        }

        public void setTag_id(long tag_id) {
            this.tag_id = tag_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        //endregion
    }

    public class ToutiaoFeedNewsItemImageItem{

        //region Member
        private String url;
        private int height;
        private int width;
        //endregion

        //region Getter and Setter
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
        //endregion
    }

}