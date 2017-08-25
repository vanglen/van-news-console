package model.enums;

/**
 * Created by Administrator on 2017/8/25.
 */
public enum EnumCommentType {
    /**
     * 缺省
     */
    DEFAULT(0),
    /**
     * 评论文章
     */
    ARTICLE(1),

    /**
     * 评论回复
     */
    COMMENT(2);

    private int value;

    private EnumCommentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
