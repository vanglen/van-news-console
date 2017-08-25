package service.comment.impl;

import dao.mapper.TCommentExtendMapper;
import dao.mapper.TCommentMapper;
import model.comment.TComment;
import org.springframework.stereotype.Service;
import service.comment.CommentService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private TCommentMapper tCommentMapper;
    @Resource
    private TCommentExtendMapper tCommentExtendMapper;

    public int add(TComment tComment) {
        return tCommentMapper.insert(tComment);
    }

    public List<TComment> select4Page(int count, Date last_datetime) {
        return tCommentExtendMapper.select4Page(last_datetime, count);
    }
}
