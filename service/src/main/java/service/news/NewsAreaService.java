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
     * 添加城市
     *
     * @param tNewsArea
     * @return
     */
    int Add(TNewsArea tNewsArea);
}
