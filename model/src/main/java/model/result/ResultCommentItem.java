package model.result;

/**
 * Created by Administrator on 2017/8/30.
 */
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
