package model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */
public class ResultCommentList<T> extends ResultBase {
    private int count = 0;
    private long last_comment_timestamp = 0;
    private T to_data;
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

    public T getTo_data() {
        return to_data;
    }

    public void setTo_data(T to_data) {
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

    public class ResultCommentItem {
        private int to_id = 0;
        private int to_type = 0;
        private int comment_id = 0;
        private String comment_content = "";
        private String comment_datetime = "";
        private int comment_is_self = 0;
        private int user_id = 0;
        private String user_name = "";
        private String user_headpic = "";

        public int getTo_id() {
            return to_id;
        }

        public void setTo_id(int to_id) {
            this.to_id = to_id;
        }

        public int getTo_type() {
            return to_type;
        }

        public void setTo_type(int to_type) {
            this.to_type = to_type;
        }

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getComment_content() {
            return comment_content;
        }

        public void setComment_content(String comment_content) {
            this.comment_content = comment_content;
        }

        public String getComment_datetime() {
            return comment_datetime;
        }

        public void setComment_datetime(String comment_datetime) {
            this.comment_datetime = comment_datetime;
        }

        public int getComment_is_self() {
            return comment_is_self;
        }

        public void setComment_is_self(int comment_is_self) {
            this.comment_is_self = comment_is_self;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_headpic() {
            return user_headpic;
        }

        public void setUser_headpic(String user_headpic) {
            this.user_headpic = user_headpic;
        }
    }
}
