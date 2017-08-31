package dao.mapper;

import model.comment.TComment;

import java.util.Date;
import java.util.List;

public interface TCommentExtendMapper {
    List<TComment> select4Page(int to_id, int to_type,int status, Date createdtime, int count);
}