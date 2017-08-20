package model.result;

public class ResultTabMy extends ResultBase {
    private int userid = 0;
    private String username = "";
    private String headpic = "";

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadrpic() {
        return headpic;
    }

    public void setHeadpic(String userpic) {
        this.headpic = userpic;
    }
}
