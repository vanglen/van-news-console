package model.param;

public class ParamTabNews extends ParamBase {
    private int city_id;
    private int catalog_id;
    private int type;
    private long last_timestamp;
    private int count;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
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

    public long getLast_timestamp() {
        return last_timestamp;
    }

    public void setLast_timestamp(long last_timestamp) {
        this.last_timestamp = last_timestamp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
