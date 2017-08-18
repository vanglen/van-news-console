package model.spider.netease;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/18.
 */

public class NeteaseHouseNewsDetail {
    private String template;
    private NeteaseHouseNewsSourceInfo sourceinfo;
    private String source;
    private String title;
    private String body;
    private boolean picnews;
    private Date ptime;
    private String ec;
    private String docid;
    private String dkeys;
    private String replyBoard;
    private String categ;

    //region Getter and Setter
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public NeteaseHouseNewsSourceInfo getSourceinfo() {
        return sourceinfo;
    }

    public void setSourceinfo(NeteaseHouseNewsSourceInfo sourceinfo) {
        this.sourceinfo = sourceinfo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isPicnews() {
        return picnews;
    }

    public void setPicnews(boolean picnews) {
        this.picnews = picnews;
    }

    public Date getPtime() {
        return ptime;
    }

    public void setPtime(Date ptime) {
        this.ptime = ptime;
    }

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDkeys() {
        return dkeys;
    }

    public void setDkeys(String dkeys) {
        this.dkeys = dkeys;
    }

    public String getReplyBoard() {
        return replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }
    //endregion
}

