package service.news.impl;

import common.util.DateUtil;
import dao.mapper.TNewsExtendMapper;
import dao.mapper.TNewsMapper;
import model.news.TNews;
import model.news.TNewsExample;
import org.springframework.stereotype.Service;
import service.news.NewsService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private TNewsMapper tNewsMapper;
    @Resource
    private TNewsExtendMapper tNewsExtendMapper;

    public List<TNews> ListByCheckTime(int count, int city_id, Date last_check_time) {
//        return tNewsExtendMapper.selectByChecktime4Page(DateUtil.getDateTime(max_check_time,"yyyy-MM-dd HH:mm:ss"), count);
        return tNewsExtendMapper.selectByChecktime4Page(last_check_time, city_id, count);
    }

    public TNews GetById(int id) {
        return tNewsMapper.selectByPrimaryKey(id);
    }

    public void Like() {

    }
}
