package service.news;

import model.newsarea.TNewsArea;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
public interface NewsAreaService {

    /**
     * 获取当前启用的城市列表
     *
     * @return 当前启用的城市列表
     */
    List<TNewsArea> ListNewsAreaUsable();

    /**
     * 根据城市名称获取城市信息
     *
     * @return 城市信息
     */
    TNewsArea GetNewsAreaByName(String city_name);

    /**
     * 添加城市
     *
     * @param tNewsArea
     * @return
     */
    int Add(TNewsArea tNewsArea);
}
