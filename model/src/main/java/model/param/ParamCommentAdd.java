package model.param;

import model.enums.EnumCommentType;

/**
 * Created by Administrator on 2017/8/25.
 */
public class ParamCommentAdd extends ParamBase {
    private int to_id;
    private int to_type;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
