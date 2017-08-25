package model.param;

/**
 * Created by Administrator on 2017/8/25.
 */
public class ParamCommentList extends ParamBase {
    private int to_id;
    private int to_type;
    private int count;
    private long last_comment_timestamp;

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
}
