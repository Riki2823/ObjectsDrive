package villanueva.ricardo.Objects.Model;

public class Version {
    private int id;
    private int idFile;
    private int idObj;
    private String date;

    public Version(int id, int idFile, int idObj, String date) {
        this.id = id;
        this.idFile = idFile;
        this.idObj = idObj;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFile() {
        return idFile;
    }

    public void setIdFile(int idFile) {
        this.idFile = idFile;
    }

    public int getIdObj() {
        return idObj;
    }

    public void setIdObj(int idObj) {
        this.idObj = idObj;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
