package dao.mapper;

import model.news.TNews;
import model.news.TNewsExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TNewsExtendMapper {
    List<TNews> selectByChecktime4Page(Date check_time, int count);
}