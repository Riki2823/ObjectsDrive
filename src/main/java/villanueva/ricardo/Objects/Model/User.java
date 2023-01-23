package villanueva.ricardo.Objects.Model;

public class User {
    private String realname;
    private String username;
    private String passwd;

    public User(String realname, String username, String passwd) {
        this.realname = realname;
        this.username = username;
        this.passwd = passwd;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
