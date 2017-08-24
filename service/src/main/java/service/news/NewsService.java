package service.news;

import model.news.TNews;

import java.util.Date;
import java.util.List;

public interface NewsService {
    /**
     * 获取资讯列表
     *
     * @param count 获取数量
     * @param last_check_time 最大时间缀
     * @return 资讯列表
     */
    List<TNews> ListByCheckTime(int count, Date last_check_time);

    /**
     * 根据ID获取资讯信息
     *
     * @param id 资讯ID
     * @return 资讯信息
     */
    TNews GetById(int id);

    /**
     * 资讯点赞
     */
    void Like();
}
