package model.param;

public class ParamTabNews extends ParamBase {
    private int city_id;
    private String city_name;
    private int catalog_id;
    private int type;
    private long last_news_timestamp;
    private int count;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(int catalog_id) {
        this.catalog_id = catalog_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getLast_news_timestamp() {
        return last_news_timestamp;
    }

    public void setLast_news_timestamp(long last_news_timestamp) {
        this.last_news_timestamp = last_news_timestamp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
