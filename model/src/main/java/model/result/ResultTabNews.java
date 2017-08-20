package model.result;

import java.util.ArrayList;
import java.util.List;

public class ResultTabNews extends ResultBase {
    private List<ResultTabNewsItem> news_data = new ArrayList<ResultTabNewsItem>();

    public List<ResultTabNewsItem> getNews_data() {
        return news_data;
    }

    public void setNews_data(List<ResultTabNewsItem> news_data) {
        this.news_data = news_data;
    }

    public class ResultTabNewsItem {
        private int news_id = 0;
        private String title = "";
        private String pic = "";
        private String source = "";
        private String tags = "";
        private int count_comment = 0;
        private int count_like = 0;
        private int count_browser = 0;
        private String url = "";
        private String news_time = "";
        private long news_timestamp = 0;

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public int getCount_comment() {
            return count_comment;
        }

        public void setCount_comment(int count_comment) {
            this.count_comment = count_comment;
        }

        public int getCount_like() {
            return count_like;
        }

        public void setCount_like(int count_like) {
            this.count_like = count_like;
        }

        public int getCount_browser() {
            return count_browser;
        }

        public void setCount_browser(int count_browser) {
            this.count_browser = count_browser;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNews_time() {
            return news_time;
        }

        public void setNews_time(String news_time) {
            this.news_time = news_time;
        }

        public long getNews_timestamp() {
            return news_timestamp;
        }

        public void setNews_timestamp(long news_timestamp) {
            this.news_timestamp = news_timestamp;
        }
    }
}
