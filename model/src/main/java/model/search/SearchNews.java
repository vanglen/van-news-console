package model.search;

import model.news.TNews;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/12.
 */
public class SearchNews extends TNews {
    private int count;
    private Date last_datetime;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getLast_datetime() {
        return last_datetime;
    }

    public void setLast_datetime(Date last_datetime) {
        this.last_datetime = last_datetime;
    }
}
