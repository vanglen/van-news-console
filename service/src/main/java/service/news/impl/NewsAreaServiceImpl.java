package service.news.impl;

import dao.mapper.TNewsAreaMapper;
import model.newsarea.TNewsArea;
import model.newsarea.TNewsAreaExample;
import service.news.NewsAreaService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
public class NewsAreaServiceImpl implements NewsAreaService {

    @Resource
    private TNewsAreaMapper tNewsAreaMapper;

    public List<TNewsArea> ListNewsAreaUsable() {
        return tNewsAreaMapper.selectByExample(new TNewsAreaExample());
    }

    public int Add(TNewsArea tNewsArea) {
        return tNewsAreaMapper.insert(tNewsArea);
    }
}
