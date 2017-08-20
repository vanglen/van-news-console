package service.news;

import model.news.TNews;

import java.util.Date;
import java.util.List;

public interface NewsService {
    /**
     * 获取资讯列表
     */
    List<TNews> ListByCheckTime(int count,Date max_check_time);

    /**
     * 资讯点赞
     */
    void Like();
}
