package service.news.impl;

import dao.mapper.TNewsAreaMapper;
import model.newsarea.TNewsArea;
import model.newsarea.TNewsAreaExample;
import org.springframework.stereotype.Service;
import service.news.NewsAreaService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
@Service
public class NewsAreaServiceImpl implements NewsAreaService {

    @Resource
    private TNewsAreaMapper tNewsAreaMapper;

    public List<TNewsArea> ListNewsAreaUsable() {
        return tNewsAreaMapper.selectByExample(new TNewsAreaExample());
    }

    public TNewsArea GetNewsAreaByName(String city_name) {
        TNewsAreaExample tNewsAreaExample = new TNewsAreaExample();
        tNewsAreaExample.createCriteria().andNameLike(city_name);
        List<TNewsArea> tNewsAreaList = tNewsAreaMapper.selectByExample(tNewsAreaExample);
        if (tNewsAreaList != null && tNewsAreaList.size() > 0) {
            return tNewsAreaList.get(0);
        }
        return null;
    }

    public int Add(TNewsArea tNewsArea) {
        return tNewsAreaMapper.insert(tNewsArea);
    }
}
