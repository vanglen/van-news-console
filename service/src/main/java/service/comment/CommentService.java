package service.comment;

import model.comment.TComment;

import java.util.Date;
import java.util.List;

public interface CommentService {
    /**
     * 发表评论
     */
    int add(TComment tComment);

    /**
     * 获取评论列表
     */
    List<TComment> select4Page(int count, Date last_datetime);
}
