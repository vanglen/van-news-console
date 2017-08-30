package model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */
public class ResultCommentList extends ResultBase {
    private int count = 0;
    private long last_comment_timestamp = 0;
    private Object to_data = new Object();
    private List<ResultCommentItem> comment_data = new ArrayList<ResultCommentItem>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getLast_comment_timestamp() {
        return last_comment_timestamp;
    }

    public void setLast_comment_timestamp(long last_comment_timestamp) {
        this.last_comment_timestamp = last_comment_timestamp;
    }

    public Object getTo_data() {
        return to_data;
    }

    public void setTo_data(Object to_data) {
        this.to_data = to_data;
    }

    public List<ResultCommentItem> getComment_data() {
        return comment_data;
    }

    public void setComment_data(List<ResultCommentItem> comment_data) {
        this.comment_data = comment_data;
    }

    public class ResultCommentNewsItem {
        private int news_id = 0;
        private int count_comment = 0;
        private int count_like = 0;
        private int count_browser = 0;

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
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
    }


}
