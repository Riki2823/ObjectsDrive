package villanueva.ricardo.Objects.Model;

public class Object {
    private int id;
    private String name;
    private String owner;
    private String bucketSrcName;
    private String date;

    public Object(int id, String name, String owner, String bucketSrcName) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.bucketSrcName = bucketSrcName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBucketSrcName() {
        return bucketSrcName;
    }

    public void setBucketSrcName(String bucketSrcName) {
        this.bucketSrcName = bucketSrcName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
